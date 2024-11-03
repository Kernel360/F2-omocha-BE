package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class BidBelowStartPriceException extends BidException {
	public BidBelowStartPriceException(Long bidPrice, Long startPrice) {
		super(
			ErrorCode.BID_BELOW_START_PRICE,
			"입찰 가격이 시작 가격보다 낮습니다. bidPrice: " + bidPrice + ", startPrice: " + startPrice
		);
	}
}
