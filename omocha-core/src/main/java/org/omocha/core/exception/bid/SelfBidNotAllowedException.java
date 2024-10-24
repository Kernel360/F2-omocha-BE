package org.omocha.core.exception.bid;

import org.omocha.client.common.code.BidCode;

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
