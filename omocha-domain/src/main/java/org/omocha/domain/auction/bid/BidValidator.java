package org.omocha.domain.auction.bid;

import org.omocha.domain.auction.Auction;

public interface BidValidator {
	void bidValidate(Auction auction, Long buyerId, Long bidPrice);

	void instantBuyValidate(Auction auction, Long buyerId);
}
