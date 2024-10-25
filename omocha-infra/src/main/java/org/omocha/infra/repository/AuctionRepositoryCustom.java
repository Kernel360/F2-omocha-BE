package org.omocha.infra.repository;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionRepositoryCustom {
	Page<Auction> searchAuctionList(AuctionCommand.SearchAuction searchAuction, Pageable pageable);
}
