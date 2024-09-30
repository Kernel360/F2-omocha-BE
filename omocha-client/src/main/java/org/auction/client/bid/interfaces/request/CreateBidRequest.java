package org.auction.client.bid.interfaces.request;

public record CreateBidRequest(
	Long bidPrice

) {
	public Long getBidPrice() {
		return bidPrice;
	}

}
