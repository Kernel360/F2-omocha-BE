package org.omocha.infra.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.AuctionEntity;
import org.omocha.domain.auction.AuctionStatus;
import org.omocha.infra.querydsl.AuctionRepositoryCustom;
import org.omocha.infra.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long>, AuctionRepositoryCustom {
	List<AuctionEntity> findByAuctionStatusAndEndDateBefore(AuctionStatus auctionStatus, LocalDateTime nowDate);
}
