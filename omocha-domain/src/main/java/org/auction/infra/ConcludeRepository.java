package org.auction.infra;

import java.util.Optional;

import org.auction.domain.bid.ConcludeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcludeRepository extends JpaRepository<ConcludeEntity, Long> {
	Optional<ConcludeEntity> findByAuctionEntityAuctionId(Long auctionId);
}
