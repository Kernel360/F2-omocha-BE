package org.omocha.infra.repository;

import static org.omocha.domain.auction.QAuction.*;
import static org.omocha.domain.auction.bid.QBid.*;
import static org.omocha.domain.image.QImage.*;

import java.util.List;

import org.omocha.domain.auction.bid.Bid;
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
	public Page<Bid> searchMyBidList(Long memberId, Pageable pageable) {

		JPAQuery<Bid> query = queryFactory
			.selectFrom(bid)
			.leftJoin(bid.auction, auction)
			.leftJoin(auction.images, image)
			.where(bid.buyer.memberId.eq(memberId));

		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(bid.getType(), bid.getMetadata());
			query.orderBy(
				new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
		}

		// 페이징 적용
		List<Bid> questions = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

		JPAQuery<Long> countQuery = queryFactory.select(bid.count()).from(bid);

		return PageableExecutionUtils.getPage(questions, pageable, countQuery::fetchOne);
	}
}