package org.omocha.domain.bid;

import org.omocha.domain.auction.vo.Price;

public class BidCommand {

	public record AddBid(
		Long auctionId,
		Long buyerMemberId,
		Price bidPrice
	) {

	}

	public record BuyNow(
		Long buyerMemberId,
		Long auctionId
	) {

	}

	public record RetrieveMyBids(
		Long memberId,
		Long auctionId
	) {

	}

}
