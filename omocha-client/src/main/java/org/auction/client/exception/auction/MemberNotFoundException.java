package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionErrorCode;

public class MemberNotFoundException extends AuctionException {
	public MemberNotFoundException(
		AuctionErrorCode errorCode
	) {
		super(errorCode);
	}

	public MemberNotFoundException(
		AuctionErrorCode errorCode,
		String detailMessage
	) {
		super(errorCode, detailMessage);
	}
}
