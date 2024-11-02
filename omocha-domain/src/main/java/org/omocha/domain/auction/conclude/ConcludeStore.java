package org.omocha.domain.auction.conclude;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.bid.Bid;

public interface ConcludeStore {
	Conclude store(Auction auction, Bid highestBid);
}
