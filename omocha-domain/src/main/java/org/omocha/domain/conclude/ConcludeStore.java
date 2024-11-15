package org.omocha.domain.conclude;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.bid.Bid;

public interface ConcludeStore {
	Conclude store(Auction auction, Bid highestBid);
}
