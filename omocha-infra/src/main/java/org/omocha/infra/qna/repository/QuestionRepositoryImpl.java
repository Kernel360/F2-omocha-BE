package org.omocha.infra.qna.repository;

import static org.omocha.domain.qna.answer.QAnswer.*;
import static org.omocha.domain.qna.question.QQuestion.*;

import java.util.List;

import org.omocha.domain.qna.Qna;
import org.omocha.domain.qna.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

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
	public Page<Qna> retrieveQnaList(Long auctionId, Pageable pageable) {
		JPAQuery<Qna> query = queryFactory
			.select(Projections.constructor(Qna.class, question, answer))
			.from(question)
			.leftJoin(answer)
			.on(question.questionId.eq(answer.question.questionId)
				.and(answer.deleted.isFalse()))
			.where(question.auction.auctionId.eq(auctionId)
				.and(question.deleted.isFalse()));

		// 정렬 처리
		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<Question> pathBuilder = new PathBuilder<>(Question.class, "question");
			query.orderBy(new OrderSpecifier(
				o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())
			));
		}

		// 페이징 적용
		List<Qna> qnaList = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(question.count())
			.from(question)
			.where(question.auction.auctionId.eq(auctionId)
				.and(question.deleted.isFalse()));

		return PageableExecutionUtils.getPage(qnaList, pageable, countQuery::fetchOne);
	}
}
