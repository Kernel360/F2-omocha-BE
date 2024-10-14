package org.auction.domain.bid.infrastructure.custom;

import static org.auction.domain.auction.domain.entity.QAuctionEntity.*;
import static org.auction.domain.bid.entity.QBidEntity.*;
import static org.auction.domain.image.domain.entity.QImageEntity.*;

import java.util.List;

import org.auction.domain.bid.entity.BidEntity;
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

	@Override
	public Page<BidEntity> searchMyBidList(Long memberId, Pageable pageable) {

		JPAQuery<BidEntity> query = queryFactory
			.selectFrom(bidEntity)
			.leftJoin(bidEntity.auctionEntity, auctionEntity)
			.leftJoin(auctionEntity.images, imageEntity)
			.where(bidEntity.buyerEntity.memberId.eq(memberId));

		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<?> pathBuilder = new PathBuilder<>(bidEntity.getType(), bidEntity.getMetadata());
			query.orderBy(
				new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
		}

		// 페이징 적용
		List<BidEntity> questions = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

		JPAQuery<Long> countQuery = queryFactory.select(bidEntity.count()).from(bidEntity);

		return PageableExecutionUtils.getPage(questions, pageable, countQuery::fetchOne);
	}
}
