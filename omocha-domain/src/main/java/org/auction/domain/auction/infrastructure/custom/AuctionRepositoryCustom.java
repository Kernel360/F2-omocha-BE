package org.auction.domain.auction.infrastructure.custom;

import java.util.Optional;

import org.auction.domain.auction.domain.entity.AuctionEntity;

public interface AuctionRepositoryCustom {
	Optional<AuctionEntity> findByIdWithImages(Long id);
}
