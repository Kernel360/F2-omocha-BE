package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionCode;

public class AuctionIllegalStateException extends AuctionException {
	public AuctionIllegalStateException(
		AuctionCode auctionCode
	) {
		super(auctionCode);
	}

	public AuctionIllegalStateException(
		AuctionCode auctionCode, String detailMessage
	) {
		super(auctionCode, detailMessage);
	}
}
