package org.omocha.domain.bid;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.member.Member;

public interface BidStore {
	Bid store(Auction auction, Member buyer, Price bidPrice);
}
