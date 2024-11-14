package org.omocha.domain.auction.bid;

public class BidCommand {

	public record AddBid(
		Long auctionId,
		Long buyerId,
		Long bidPrice
	) {

	}

	public record BuyNow(
		Long memberId,
		Long auctionId
	) {

	}
}
