package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionCode;

public class AuctionAlreadyEndedException extends AuctionException {
	public AuctionAlreadyEndedException(
		AuctionCode auctionCode
	) {
		super(auctionCode);
	}

	public AuctionAlreadyEndedException(
		AuctionCode auctionCode,
		String detailMessage
	) {
		super(auctionCode, detailMessage);
	}
}
