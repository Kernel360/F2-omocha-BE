package org.omocha.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionReader {
	Page<AuctionInfo.AuctionListResponse> searchAuctionList(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	);

	Auction findAuction(Long auctionId);

	// Page<Auction> searchMyAuctionList(Long memberId, AuctionStatus auctionStatus, Pageable pageable);
}
