package org.auction.domain.auction.infrastructure.custom;

import java.util.Optional;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.infrastructure.condition.AuctionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionRepositoryCustom {
	Optional<AuctionEntity> findByIdWithImages(
		Long id
	);

	Page<AuctionEntity> searchAuctionList(
		AuctionSearchCondition condition,
		AuctionStatus auctionStatus,
		Pageable pageable
	);

	Page<AuctionEntity> searchMyAuctionList(
		Long memberId,
		AuctionStatus auctionStatus,
		Pageable pageable
	);
}
