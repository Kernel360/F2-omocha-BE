package org.omocha.domain.bid.validate;

import org.omocha.domain.auction.Auction;

public interface BidValidator {
	void bidValidate(Auction auction, Long buyerMemberId, Long bidPrice);

	void instantBuyValidate(Auction auction, Long buyerMemberId);
}
