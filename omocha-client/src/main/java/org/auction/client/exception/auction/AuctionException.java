package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionCode;

import lombok.Getter;

@Getter
public class AuctionException extends RuntimeException {

	private final AuctionCode auctionCode;
	private final String detailMessage;

	public AuctionException(
		AuctionCode auctionCode
	) {
		super(auctionCode.getResultMsg());
		this.auctionCode = auctionCode;
		this.detailMessage = auctionCode.getResultMsg();
	}

	public AuctionException(
		AuctionCode auctionCode,
		String detailMessage
	) {
		super(auctionCode.getResultMsg());
		this.auctionCode = auctionCode;
		this.detailMessage = detailMessage;
	}

}
