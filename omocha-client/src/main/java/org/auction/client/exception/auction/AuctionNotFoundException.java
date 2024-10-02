package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionCode;

public class AuctionNotFoundException extends AuctionException {
	public AuctionNotFoundException(
		AuctionCode auctionCode
	) {
		super(auctionCode);
	}

	public AuctionNotFoundException(
		AuctionCode auctionCode,
		String detailMessage
	) {
		super(auctionCode, detailMessage);
	}
}
