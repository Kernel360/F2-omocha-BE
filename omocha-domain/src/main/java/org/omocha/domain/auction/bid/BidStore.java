package org.omocha.domain.auction.bid;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;

public interface BidStore {
	Bid store(Auction auction, Member buyer, Long bidPrice);
}
