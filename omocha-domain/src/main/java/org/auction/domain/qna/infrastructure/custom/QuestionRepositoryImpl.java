package org.auction.domain.qna.infrastructure.custom;

import static org.auction.domain.qna.domain.entity.QAnswerEntity.*;
import static org.auction.domain.qna.domain.entity.QQuestionEntity.*;

import java.util.List;

import org.auction.domain.qna.domain.entity.AnswerEntity;
import org.auction.domain.qna.domain.entity.QuestionEntity;
import org.auction.domain.qna.domain.response.QnaDomainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public QuestionRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<QuestionEntity> findQuestionList(Long auctionId, Pageable pageable) {

		JPAQuery<QuestionEntity> query = queryFactory
			.selectFrom(questionEntity)
			.where(questionEntity.auctionEntity.auctionId.eq(auctionId))
			.where(questionEntity.deleted.isFalse());

		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(
				questionEntity.getType(),
				questionEntity.getMetadata()
			);
			query.orderBy(new OrderSpecifier(
				o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())
			));
		}

		// 페이징 적용
		List<QuestionEntity> questions = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(questionEntity.count())
			.from(questionEntity);

		return PageableExecutionUtils.getPage(questions, pageable, countQuery::fetchOne);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<QnaDomainResponse> findQnaList(Long auctionId, Pageable pageable) {

		// 질문을 필터링하는 서브쿼리
		JPAQuery<QuestionEntity> questionSubquery = queryFactory
			.selectFrom(questionEntity)
			.where(questionEntity.auctionEntity.auctionId.eq(auctionId)
				.and(questionEntity.deleted.isFalse())); // 서브쿼리 실행

		// 답변을 필터링하는 서브쿼리
		JPAQuery<AnswerEntity> answerSubquery = queryFactory
			.selectFrom(answerEntity)
			.where(answerEntity.deleted.isFalse()); // 서브쿼리 실행

		// 메인 쿼리
		JPAQuery<QnaDomainResponse> query = queryFactory
			.select(Projections.constructor(QnaDomainResponse.class, questionEntity, answerEntity))
			.from(questionEntity) // 직접 questionEntity를 사용
			.leftJoin(answerEntity) // answerEntity와 leftJoin
			.on(questionEntity.questionId.eq(answerEntity.questionEntity.questionId))
			.where(
				questionEntity.in(questionSubquery)
					.and(
						answerEntity.in(answerSubquery)
							.or(answerEntity.isNull())
					)
					.and(questionEntity.auctionEntity.auctionId.eq(auctionId))
			);

		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(
				questionEntity.getType(),
				questionEntity.getMetadata()
			);
			query.orderBy(new OrderSpecifier(
				o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())
			));
		}

		// 페이징 적용
		List<QnaDomainResponse> questions = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(questionEntity.count())
			.from(questionEntity);

		return PageableExecutionUtils.getPage(questions, pageable, countQuery::fetchOne);

	}

}
