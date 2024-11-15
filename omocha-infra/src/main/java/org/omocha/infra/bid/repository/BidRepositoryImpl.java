package org.omocha.infra.bid.repository;

import static org.omocha.domain.auction.QAuction.*;
import static org.omocha.domain.bid.QBid.*;

import java.util.List;

import org.omocha.domain.bid.BidInfo;
import org.omocha.domain.bid.QBidInfo_RetrieveMyBids;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class BidRepositoryImpl implements BidRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public BidRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	// TODO: Mypage 진행하며 확인 필요

	@Override
	public Page<BidInfo.RetrieveMyBids> getMyBidList(Long memberId, Long auctionId, Pageable sortPage) {
		JPAQuery<BidInfo.RetrieveMyBids> query = queryFactory
			.select(new QBidInfo_RetrieveMyBids(
				bid.bidPrice,
				bid.createdAt
			))
			.from(bid)
			.leftJoin(bid.auction, auction)
			.where(bid.buyer.memberId.eq(memberId).and(auction.auctionId.eq(auctionId)));

		for (Sort.Order o : sortPage.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(bid.getType(), bid.getMetadata());
			query.orderBy(
				new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
		}

		// 페이징 적용
		List<BidInfo.RetrieveMyBids> myBids = query.offset(sortPage.getOffset())
			.limit(sortPage.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory.select(bid.count()).from(bid);

		return PageableExecutionUtils.getPage(myBids, sortPage, countQuery::fetchOne);
	}

}