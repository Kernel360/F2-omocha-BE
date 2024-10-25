package org.omocha.infra.repository;

import static org.omocha.domain.auction.QAuction.*;
import static org.springframework.util.ObjectUtils.*;

import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionStatus;
import org.omocha.domain.auction.QAuction;
import org.omocha.domain.image.QImage;
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

	public Page<Auction> searchAuctionList(AuctionCommand.SearchAuction searchAuction, Pageable pageable) {
		QAuction auction = QAuction.auction;
		QImage image = QImage.image;

		JPAQuery<Auction> query = queryFactory
			.selectDistinct(auction)
			.from(auction)
			.leftJoin(auction.images, image)
			.where(
				titleContains(searchAuction.title()),
				statusEquals(searchAuction.auctionStatus())
			);

		applySorting(pageable, auction, query);

		// 페이징 적용
		List<Auction> auctions = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(auction.count())
			.from(auction)
			.where(
				titleContains(searchAuction.title()),
				statusEquals(searchAuction.auctionStatus())
			);

		return PageableExecutionUtils.getPage(auctions, pageable, countQuery::fetchOne);
	}

	private static void applySorting(Pageable pageable, QAuction auction, JPAQuery<Auction> query) {
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

	private BooleanExpression statusEquals(AuctionStatus auctionStatus) {
		return auctionStatus == null ? null : auction.auctionStatus.eq(auctionStatus);
	}

}
