package org.auction.domain.bid.infrastructure;

import java.util.List;
import java.util.Optional;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.bid.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<BidEntity, Long> {

	Optional<BidEntity> findTopByAuctionEntityOrderByBidPriceDesc(AuctionEntity auctionEntity);

	List<BidEntity> findAllByAuctionEntityOrderByCreatedAtDesc(AuctionEntity auctionEntity);

}
