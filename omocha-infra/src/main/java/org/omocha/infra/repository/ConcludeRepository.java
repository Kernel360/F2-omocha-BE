package org.omocha.infra.repository;

import java.util.Optional;

import org.omocha.domain.auction.conclude.Conclude;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcludeRepository extends JpaRepository<Conclude, Long> {

	Optional<Conclude> findByAuctionAuctionId(Long auctionId);
}
