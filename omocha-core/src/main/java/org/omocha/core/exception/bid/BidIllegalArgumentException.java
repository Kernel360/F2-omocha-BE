package org.omocha.core.exception.bid;

import org.omocha.client.common.code.BidCode;

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
