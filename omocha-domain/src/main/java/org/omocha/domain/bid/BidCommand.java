package org.omocha.domain.bid;

public class BidCommand {

	public record AddBid(
		Long auctionId,
		Long buyerMemberId,
		Long bidPrice
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
