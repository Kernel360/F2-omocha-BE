package org.auction.client.exception.bid;

import org.auction.client.common.code.BidCode;

public class SelfBidNotAllowedException extends BidException {
	public SelfBidNotAllowedException(
		BidCode bidCode
	) {
		super(bidCode);
	}

	public SelfBidNotAllowedException(
		BidCode bidCode,
		String detailMessage
	) {
		super(bidCode, detailMessage);
	}
}
