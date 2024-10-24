package org.omocha.infra.querydsl;

import java.util.Optional;

import org.omocha.domain.auction.AuctionStatus;
import org.omocha.infra.condition.AuctionSearchCondition;
import org.omocha.infra.entity.AuctionEntity;
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
