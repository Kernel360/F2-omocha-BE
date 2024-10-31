package org.auction.client.exception.bid;

import org.auction.client.common.code.BidCode;

import lombok.Getter;

@Getter
public class BidException extends RuntimeException {
	private final BidCode bidCode;
	private final String detailMessage;

	public BidException(
		BidCode bidCode
	) {
		super(bidCode.getResultMsg());
		this.bidCode = bidCode;
		this.detailMessage = bidCode.getResultMsg();
	}

	public BidException(
		BidCode bidCode,
		String detailMessage
	) {
		super(bidCode.getResultMsg());
		this.bidCode = bidCode;
		this.detailMessage = detailMessage;
	}
}
