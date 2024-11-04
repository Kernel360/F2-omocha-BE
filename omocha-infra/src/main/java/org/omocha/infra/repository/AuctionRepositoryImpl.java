package org.omocha.infra.repository;

import static org.omocha.domain.auction.QAuction.*;
import static org.springframework.util.ObjectUtils.*;

import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.QAuction;
import org.omocha.domain.auction.QAuctionInfo_SearchAuction;
import org.omocha.domain.auction.conclude.QConclude;
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
public class AuctionRepositoryImpl implements AuctionRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public AuctionRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	public Page<AuctionInfo.SearchAuction> getAuctionList(
		AuctionCommand.SearchAuction searchAuction, Pageable pageable) {

		QAuction auction = QAuction.auction;
		QConclude conclude = QConclude.conclude;

		JPAQuery<AuctionInfo.SearchAuction> query = queryFactory
			.select(new QAuctionInfo_SearchAuction(
				auction.auctionId,
				auction.memberId,
				auction.title,
				auction.content,
				auction.startPrice,
				auction.bidUnit,
				auction.auctionStatus,
				auction.thumbnailPath,
				auction.nowPrice,
				conclude.concludePrice,
				auction.bidCount,
				auction.startDate,
				auction.endDate,
				auction.createdAt
			))
			.from(auction)
			.leftJoin(conclude).on(conclude.auction.eq(auction))
			.where(
				titleContains(searchAuction.title()),
				statusEquals(searchAuction.auctionStatus())
			);

		applySorting(pageable, auction, query);

		List<AuctionInfo.SearchAuction> results = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = getCountQuery(searchAuction, auction);

		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
	}

	private JPAQuery<Long> getCountQuery(AuctionCommand.SearchAuction searchAuction, QAuction auction) {
		return queryFactory
			.select(auction.count())
			.from(auction)
			.where(
				titleContains(searchAuction.title()),
				statusEquals(searchAuction.auctionStatus())
			);
	}

	private static <T> void applySorting(
		Pageable pageable,
		QAuction auction,
		JPAQuery<T> query
	) {
		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(
				auction.getType(),
				auction.getMetadata()
			);
			query.orderBy(new OrderSpecifier(
				o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())
			));
		}
	}

	private BooleanExpression titleContains(String title) {
		return isEmpty(title) ? null : auction.title.containsIgnoreCase(title);
	}

	private BooleanExpression statusEquals(Auction.AuctionStatus auctionStatus) {
		return auctionStatus == null ? null : auction.auctionStatus.eq(auctionStatus);
	}

	// public Page<Auction> searchMyAuctionList(Long memberId, AuctionStatus auctionStatus, Pageable pageable) {
	//
	// 	JPAQuery<Auction> query = queryFactory
	// 		.selectFrom(auction)
	// 		.leftJoin(auction.images, image)
	// 		.where(auction.member.memberId.eq(memberId)
	// 			.and(statusEquals(auctionStatus)));
	//
	// 	for (Sort.Order o : pageable.getSort()) {
	// 		PathBuilder<?> pathBuilder = new PathBuilder<>(
	// 			auction.getType(),
	// 			auction.getMetadata()
	// 		);
	// 		query.orderBy(new OrderSpecifier(
	// 			o.isAscending() ? Order.ASC : Order.DESC,
	// 			pathBuilder.get(o.getProperty())
	// 		));
	// 	}
	//
	// 	// 페이징 적용
	// 	List<Auction> auctions = query
	// 		.offset(pageable.getOffset())
	// 		.limit(pageable.getPageSize())
	// 		.fetch();
	//
	// 	JPAQuery<Long> countQuery = queryFactory
	// 		.select(auction.count())
	// 		.from(auction);
	//
	// 	return PageableExecutionUtils.getPage(auctions, pageable, countQuery::fetchOne);
	// }

}
