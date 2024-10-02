package org.auction.domain.bid.infrastructure;

import java.util.List;
import java.util.Optional;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.bid.entity.BidEntity;
import org.auction.domain.user.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<BidEntity, Long> {

	Optional<BidEntity> findTopByMemberEntityOrderByBidPriceDesc(MemberEntity memberEntity);

	Optional<BidEntity> findTopByMemberEntityOrderByCreatedAtDesc(MemberEntity memberEntity);

	List<BidEntity> findAllByAuctionEntityOrderByCreatedAtDesc(AuctionEntity auctionEntity);

}
