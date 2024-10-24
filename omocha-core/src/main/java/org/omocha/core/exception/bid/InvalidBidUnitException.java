package org.omocha.core.exception.bid;

import org.omocha.client.common.code.BidCode;

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
