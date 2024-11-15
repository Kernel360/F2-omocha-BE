package org.omocha.domain.bid.exception;

import org.omocha.domain.common.code.ErrorCode;

public class BidExceedsInstantBuyException extends BidException {
	public BidExceedsInstantBuyException(Long bidPrice, Long instantBuyPrice) {
		super(
			ErrorCode.BID_BELOW_START_PRICE,
			"입찰 가격이 즉시 구매 가격과 같거나 높습니다. bidPrice: " + bidPrice + ", instantBuyPrice: " + instantBuyPrice
		);
	}
}
