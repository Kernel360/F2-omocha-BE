package org.auction.domain.bid.infrastructure;

import java.util.Optional;

import org.auction.domain.bid.entity.ConcludeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcludeRepository extends JpaRepository<ConcludeEntity, Long> {
	Optional<ConcludeEntity> findByAuctionEntityAuctionId(Long auctionId);
}
