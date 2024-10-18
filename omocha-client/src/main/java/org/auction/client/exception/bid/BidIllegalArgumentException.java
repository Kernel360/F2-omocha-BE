package org.auction.client.exception.bid;

import org.auction.client.common.code.BidCode;

public class BidIllegalArgumentException extends BidException {
	public BidIllegalArgumentException(
		BidCode bidCode
	) {
		super(bidCode);
	}

	public BidIllegalArgumentException(
		BidCode bidCode,
		String detailMessage
	) {
		super(bidCode, detailMessage);
	}
}
