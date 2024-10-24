package org.omocha.infra.jpa;

import java.util.List;
import java.util.Optional;

import org.omocha.domain.auction.AuctionEntity;
import org.omocha.domain.bid.BidEntity;
import org.omocha.infra.querydsl.BidRepositoryCustom;
import org.omocha.infra.entity.AuctionEntity;
import org.omocha.infra.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<BidEntity, Long>, BidRepositoryCustom {

	Optional<BidEntity> findTopByAuctionEntityOrderByBidPriceDesc(AuctionEntity auctionEntity);

	List<BidEntity> findAllByAuctionEntityOrderByCreatedAtDesc(AuctionEntity auctionEntity);

	Long countByAuctionEntity(AuctionEntity auctionEntity);

}
