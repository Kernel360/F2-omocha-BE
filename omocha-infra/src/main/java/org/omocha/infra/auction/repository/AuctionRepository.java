package org.omocha.infra.auction.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
	List<Auction> findAllByAuctionStatusAndEndDateBefore(Auction.AuctionStatus status, LocalDateTime endDate);

}
