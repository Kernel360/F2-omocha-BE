package org.omocha.infra.repository;

import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionRepositoryCustom {
	Page<AuctionInfo.SearchAuction> getAuctionList(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	);
	// Page<Auction> searchMyAuctionList(
	// 	Long memberId,
	// 	AuctionStatus auctionStatus,
	// 	Pageable pageable
	// );
}
