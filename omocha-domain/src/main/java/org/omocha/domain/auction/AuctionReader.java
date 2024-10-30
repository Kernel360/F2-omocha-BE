package org.omocha.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionReader {
	Page<Auction> searchAuctionList(AuctionCommand.SearchAuction searchAuction, Pageable pageable);

	// Page<Auction> searchMyAuctionList(Long memberId, AuctionStatus auctionStatus, Pageable pageable);
}
