package org.omocha.infra.repository;

import org.omocha.infra.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> /*AuctionReader*/ {
}
