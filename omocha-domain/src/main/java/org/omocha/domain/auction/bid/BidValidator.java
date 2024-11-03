package org.omocha.domain.auction.bid;

import org.omocha.domain.auction.Auction;

public interface BidValidator {
	void validate(Auction auction, Long buyerId, Long bidPrice);
}
