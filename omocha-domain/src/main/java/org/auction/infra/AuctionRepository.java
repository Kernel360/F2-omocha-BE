package org.auction.infra;

import java.time.LocalDateTime;
import java.util.List;

import org.auction.domain.auction.AuctionEntity;
import org.auction.domain.auction.AuctionStatus;
import org.auction.infra.custom.AuctionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long>, AuctionRepositoryCustom {
	List<AuctionEntity> findByAuctionStatusAndEndDateBefore(AuctionStatus auctionStatus, LocalDateTime nowDate);
}
