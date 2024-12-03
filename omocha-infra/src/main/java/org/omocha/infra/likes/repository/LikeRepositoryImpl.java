package org.omocha.infra.likes.repository;

import static org.omocha.domain.auction.QAuction.*;
import static org.omocha.domain.likes.QLikes.*;

import java.util.List;

import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.QAuction;
import org.omocha.domain.auction.QAuctionInfo_RetrieveMyAuctionLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class LikeRepositoryImpl implements LikeRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public LikeRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<AuctionInfo.RetrieveMyAuctionLikes> getMyAuctionLikes(Long memberId, Pageable pageable) {

		JPAQuery<AuctionInfo.RetrieveMyAuctionLikes> query = queryFactory
			.select(new QAuctionInfo_RetrieveMyAuctionLikes(
				auction.auctionId,
				auction.category.categoryId,
				auction.title,
				auction.thumbnailPath,
				auction.startPrice,
				auction.nowPrice,
				auction.instantBuyPrice,
				auction.auctionStatus,
				auction.startDate,
				auction.endDate,
				auction.createdAt,
				likes.createdAt
			))
			.from(likes)
			.leftJoin(likes.auction, auction)
			.where(likes.member.memberId.eq(memberId));

		// 정렬 적용 (필요한 경우)
		applySorting(pageable, auction, query);

		// 페이징 적용
		List<AuctionInfo.RetrieveMyAuctionLikes> results = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 총 개수 조회 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(likes.count())
			.from(likes)
			.where(likes.member.memberId.eq(memberId));

		// 빈 리스트가 반환되더라도 Page 객체를 반환하므로 null이 아님
		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
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
}
