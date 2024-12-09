package org.omocha.infra.bid.repository;

import org.omocha.domain.bid.Bid;
import org.omocha.domain.bid.BidCacheDto;

public interface BidCacheRepository {
	BidCacheDto findHighestBid(Long auctionId);

	void storeHighestBid(Long auctionId, Bid bid);
}
