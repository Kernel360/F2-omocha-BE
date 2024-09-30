package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionCode;

public class AuctionNotFoundException extends AuctionException {
	public AuctionNotFoundException(
		AuctionCode errorCode
	) {
		super(errorCode);
	}

	public AuctionNotFoundException(
		AuctionCode errorCode,
		String detailMessage
	) {
		super(errorCode, detailMessage);
	}
}
