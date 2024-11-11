package org.omocha.infra.repository;

import java.util.List;

import org.omocha.domain.auction.QAuction;
import org.omocha.domain.auction.review.QReview;
import org.omocha.domain.auction.review.QReviewInfo_RetrieveReviews;
import org.omocha.domain.auction.review.Review;
import org.omocha.domain.auction.review.ReviewCommand;
import org.omocha.domain.auction.review.ReviewInfo;
import org.omocha.domain.member.QMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
		QReview review = QReview.review;
		QMember member = QMember.member;
		QAuction auction = QAuction.auction;

		BooleanBuilder builderByJoin = new BooleanBuilder();
		BooleanBuilder builderByWhere = new BooleanBuilder();

		if (retrieveReviews.category().equals(Review.Category.RECEIVED)) {
			builderByJoin.and(member.memberId.eq(review.reviewer.memberId));
			builderByWhere.and(review.recipient.memberId.eq(retrieveReviews.memberId()));
		} else {
			builderByJoin.and(member.memberId.eq(review.recipient.memberId));
			builderByWhere.and(review.reviewer.memberId.eq(retrieveReviews.memberId()));
		}

		JPAQuery<ReviewInfo.RetrieveReviews> query = queryFactory
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
			.leftJoin(member).on(builderByJoin)
			.where(builderByWhere);

		applySorting(pageable, review, query);

		List<ReviewInfo.RetrieveReviews> results = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = getCountQuery(review, builderByWhere);

		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
	}

	private JPAQuery<Long> getCountQuery(QReview review, BooleanBuilder builderByWhere) {
		return queryFactory
			.select(review.count())
			.from(review)
			.where(builderByWhere);
	}

	private static <T> void applySorting(
		Pageable pageable,
		QReview review,
		JPAQuery<T> query
	) {
		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(
				review.getType(),
				review.getMetadata()
			);
			query.orderBy(new OrderSpecifier(
				o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())
			));
		}
	}
}
