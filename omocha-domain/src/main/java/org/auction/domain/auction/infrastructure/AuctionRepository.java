package org.auction.domain.auction.infrastructure;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.infrastructure.custom.AuctionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long>, AuctionRepositoryCustom {

}
