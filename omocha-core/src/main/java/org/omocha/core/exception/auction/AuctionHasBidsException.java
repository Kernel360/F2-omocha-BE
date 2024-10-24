package org.omocha.core.exception.auction;

import org.omocha.client.common.code.AuctionCode;

public class AuctionHasBidsException extends AuctionException {
	public AuctionHasBidsException(
		AuctionCode auctionCode
	) {
		super(auctionCode);
	}

	public AuctionHasBidsException(
		AuctionCode auctionCode,
		String detailMessage
	) {
		super(auctionCode, detailMessage);
	}
}
