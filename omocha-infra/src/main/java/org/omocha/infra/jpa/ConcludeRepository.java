package org.omocha.infra.jpa;

import java.util.Optional;

import org.omocha.domain.bid.ConcludeEntity;
import org.omocha.infra.entity.ConcludeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcludeRepository extends JpaRepository<ConcludeEntity, Long> {
	Optional<ConcludeEntity> findByAuctionEntityAuctionId(Long auctionId);
}
