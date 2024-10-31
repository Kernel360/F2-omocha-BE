package org.auction.client.exception.bid;

import org.auction.client.common.code.BidCode;

public class InvalidBidUnitException extends BidException {
	public InvalidBidUnitException(
		BidCode bidCode
	) {
		super(bidCode);
	}

	public InvalidBidUnitException(
		BidCode bidCode,
		String detailMessage
	) {
		super(bidCode, detailMessage);
	}
}
