package org.omocha.core.exception.auction;

import org.omocha.client.common.code.AuctionCode;

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
