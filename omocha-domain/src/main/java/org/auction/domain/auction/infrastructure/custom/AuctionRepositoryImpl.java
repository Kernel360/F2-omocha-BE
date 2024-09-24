package org.auction.domain.auction.infrastructure.custom;

import static org.auction.domain.image.domain.entity.QImageEntity.*;

import java.util.Optional;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.domain.entity.QAuctionEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class AuctionRepositoryImpl implements AuctionRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public AuctionRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<AuctionEntity> findByIdWithImages(Long id) {

		AuctionEntity auctionEntity = queryFactory
			.selectFrom(QAuctionEntity.auctionEntity)
			.leftJoin(QAuctionEntity.auctionEntity.images, imageEntity).fetchJoin()
			.where(QAuctionEntity.auctionEntity.auctionId.eq(id))
			.fetchOne();

		return Optional.ofNullable(auctionEntity);
	}
}
