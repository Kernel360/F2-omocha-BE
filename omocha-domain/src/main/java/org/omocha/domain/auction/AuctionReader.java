package org.omocha.domain.auction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionReader {
	Page<AuctionInfo.SearchAuction> getAuctionList(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	);

	Auction getAuction(Long auctionId);

	List<Auction> getExpiredBiddingAuctionList();

	void removeAuction(Auction auction);

	// Page<Auction> searchMyAuctionList(Long memberId, AuctionStatus auctionStatus, Pageable pageable);
}
