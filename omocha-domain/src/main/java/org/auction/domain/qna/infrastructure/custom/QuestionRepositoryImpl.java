package org.auction.domain.qna.infrastructure.custom;

import static org.auction.domain.qna.domain.entity.QAnswerEntity.*;
import static org.auction.domain.qna.domain.entity.QQuestionEntity.*;

import java.util.List;

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
	public Page<QnaDomainResponse> findQnaList(
		Long auctionId,
		Pageable pageable
	) {
		JPAQuery<QnaDomainResponse> query = queryFactory
			.select(Projections.constructor(QnaDomainResponse.class, questionEntity, answerEntity))
			.from(questionEntity)
			.leftJoin(answerEntity)
			.on(questionEntity.questionId.eq(answerEntity.questionEntity.questionId)
				.and(answerEntity.deleted.isFalse()))
			.where(questionEntity.auctionEntity.auctionId.eq(auctionId)
				.and(questionEntity.deleted.isFalse()));

		// 정렬 처리
		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<QuestionEntity> pathBuilder = new PathBuilder<>(QuestionEntity.class, "questionEntity");
			query.orderBy(new OrderSpecifier(
				o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())
			));
		}

		// 페이징 적용
		List<QnaDomainResponse> qnaList = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(questionEntity.count())
			.from(questionEntity)
			.where(questionEntity.auctionEntity.auctionId.eq(auctionId)
				.and(questionEntity.deleted.isFalse()));

		return PageableExecutionUtils.getPage(qnaList, pageable, countQuery::fetchOne);
	}

}
