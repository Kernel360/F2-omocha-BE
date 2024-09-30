package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionErrorCode;

public class ImageDeletionException extends AuctionException {
	public ImageDeletionException(
		AuctionErrorCode errorCode
	) {
		super(errorCode);
	}

	public ImageDeletionException(
		AuctionErrorCode errorCode,
		String detailMessage
	) {
		super(errorCode, detailMessage);
	}
}
