package org.omocha.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionReader {
	Page<Auction> searchAuctionList(AuctionCommand.SearchAuction searchAuction, Pageable pageable);

	Auction findByAuctionId(Long auctionId);
}
