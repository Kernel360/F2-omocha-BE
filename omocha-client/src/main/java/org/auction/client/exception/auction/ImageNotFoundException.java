package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionErrorCode;

public class ImageNotFoundException extends AuctionException {
	public ImageNotFoundException(
		AuctionErrorCode errorCode
	) {
		super(errorCode);
	}

	public ImageNotFoundException(
		AuctionErrorCode errorCode,
		String detailMessage
	) {
		super(errorCode, detailMessage);
	}
}
