package org.omocha.domain.bid.validate;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.vo.Price;

public interface BidValidator {
	void bidValidate(Auction auction, Long buyerMemberId, Price bidPrice);

	void instantBuyValidate(Auction auction, Long buyerMemberId);
}
