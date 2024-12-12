package org.omocha.infra.bid.repository;

import java.util.List;
import java.util.Optional;

import org.omocha.domain.bid.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {

	List<Bid> findAllByAuctionAuctionIdOrderByCreatedAtDesc(Long auctionId);

	Optional<Bid> findTopByAuctionAuctionIdOrderByBidPriceDesc(Long auctionId);

	@Query("SELECT b.buyer.memberId FROM Bid b WHERE b.auction.auctionId = :auctionId GROUP BY b.buyer.memberId")
	List<Long> findGroupedBuyerMemberIdsByAuctionId(@Param("auctionId") Long auctionId);
}
