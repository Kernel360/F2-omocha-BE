package org.auction.client.exception.auction;

import org.auction.client.common.code.AuctionErrorCode;

import lombok.Getter;

@Getter
public class AuctionException extends RuntimeException {

	private final AuctionErrorCode auctionErrorCode;
	private final String detailMessage;

	public AuctionException(
		AuctionErrorCode errorCode
	) {
		super(errorCode.getResultMsg());
		this.auctionErrorCode = errorCode;
		this.detailMessage = errorCode.getResultMsg();
	}

	public AuctionException(
		AuctionErrorCode errorCode,
		String detailMessage
	) {
		super(errorCode.getResultMsg());
		this.auctionErrorCode = errorCode;
		this.detailMessage = detailMessage;
	}

}
