package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionErrorCode;

public class AuctionNotFoundException extends AuctionException {
	public AuctionNotFoundException(
		AuctionErrorCode errorCode
	) {
		super(errorCode);
	}

	public AuctionNotFoundException(
		AuctionErrorCode errorCode,
		String detailMessage
	) {
		super(errorCode, detailMessage);
	}
}
