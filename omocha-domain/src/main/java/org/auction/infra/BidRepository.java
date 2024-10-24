package org.auction.infra;

import java.util.List;
import java.util.Optional;

import org.auction.domain.auction.AuctionEntity;
import org.auction.domain.bid.BidEntity;
import org.auction.infra.custom.BidRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<BidEntity, Long>, BidRepositoryCustom {

	Optional<BidEntity> findTopByAuctionEntityOrderByBidPriceDesc(AuctionEntity auctionEntity);

	List<BidEntity> findAllByAuctionEntityOrderByCreatedAtDesc(AuctionEntity auctionEntity);

	Long countByAuctionEntity(AuctionEntity auctionEntity);

}
