package org.auction.client.exception.bid;

import org.auction.client.common.code.BidCode;

public class NoBidsException extends BidException {
	public NoBidsException(
		BidCode bidCode
	) {
		super(bidCode);
	}

	public NoBidsException(
		BidCode bidCode,
		String detailMessage
	) {
		super(bidCode, detailMessage);
	}
}
