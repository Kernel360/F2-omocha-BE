package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionCode;

import lombok.Getter;

@Getter
public class AuctionException extends RuntimeException {

	private final AuctionCode auctionCode;
	private final String detailMessage;

	public AuctionException(
		AuctionCode errorCode
	) {
		super(errorCode.getResultMsg());
		this.auctionCode = errorCode;
		this.detailMessage = errorCode.getResultMsg();
	}

	public AuctionException(
		AuctionCode errorCode,
		String detailMessage
	) {
		super(errorCode.getResultMsg());
		this.auctionCode = errorCode;
		this.detailMessage = detailMessage;
	}

}
