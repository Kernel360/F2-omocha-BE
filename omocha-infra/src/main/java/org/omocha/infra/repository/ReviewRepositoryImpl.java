package org.omocha.infra.repository;

import java.util.List;

import org.omocha.domain.auction.QAuction;
import org.omocha.domain.auction.review.QReview;
import org.omocha.domain.auction.review.QReviewInfo_RetrieveReviews;
import org.omocha.domain.auction.review.ReviewCommand;
import org.omocha.domain.auction.review.ReviewInfo;
import org.omocha.domain.member.QMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public ReviewRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<ReviewInfo.RetrieveReviews> getReceivedReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return getReviews(retrieveReviews, pageable, true);
	}

	@Override
	public Page<ReviewInfo.RetrieveReviews> getGivenReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return getReviews(retrieveReviews, pageable, false);
	}

	private Page<ReviewInfo.RetrieveReviews> getReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable,
		boolean isReceived
	) {
		QReview review = QReview.review;
		QMember member = QMember.member;
		QAuction auction = QAuction.auction;

		JPAQuery<ReviewInfo.RetrieveReviews> query = createReviewQuery(
			retrieveReviews,
			member,
			auction,
			review,
			isReceived
		);
		reviewQuerySorting(pageable, review, query);

		List<ReviewInfo.RetrieveReviews> reviewListResult = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = createCountQuery(retrieveReviews, review, isReceived);

		return PageableExecutionUtils.getPage(reviewListResult, pageable, countQuery::fetchOne);
	}

	private JPAQuery<ReviewInfo.RetrieveReviews> createReviewQuery(
		ReviewCommand.RetrieveReviews retrieveReviews,
		QMember member,
		QAuction auction,
		QReview review,
		boolean isReceived
	) {
		return queryFactory
			.select(new QReviewInfo_RetrieveReviews(
				member.memberId,
				member.nickname,
				auction.auctionId,
				auction.title,
				auction.thumbnailPath,
				review.reviewType,
				review.rating,
				review.content,
				review.createdAt
			))
			.from(review)
			.leftJoin(auction).on(auction.auctionId.eq(review.auction.auctionId))
			.leftJoin(member).on(getJoinCondition(member, review, isReceived))
			.where(getWhereCondition(retrieveReviews, review, isReceived));
	}

	private JPAQuery<Long> createCountQuery(
		ReviewCommand.RetrieveReviews retrieveReviews,
		QReview review,
		boolean isReceived
	) {
		return queryFactory
			.select(review.count())
			.from(review)
			.where(getWhereCondition(retrieveReviews, review, isReceived));
	}

	private static <T> void reviewQuerySorting(
		Pageable pageable,
		QReview review,
		JPAQuery<T> query
	) {
		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(review.getType(), review.getMetadata());
			query.orderBy(new OrderSpecifier(
				o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())
			));
		}
	}

	private BooleanExpression getWhereCondition(
		ReviewCommand.RetrieveReviews retrieveReviews,
		QReview review,
		boolean isReceived
	) {
		return isReceived
			? review.recipient.memberId.eq(retrieveReviews.memberId())
			: review.reviewer.memberId.eq(retrieveReviews.memberId());
	}

	private BooleanExpression getJoinCondition(
		QMember member,
		QReview review,
		boolean isReceived
	) {
		return isReceived
			? member.memberId.eq(review.reviewer.memberId)
			: member.memberId.eq(review.recipient.memberId);
	}
}