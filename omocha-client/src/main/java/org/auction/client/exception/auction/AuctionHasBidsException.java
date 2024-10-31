package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionCode;

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
