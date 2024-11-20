package org.omocha.domain.bid.exception;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.common.code.ErrorCode;

public class BidBelowStartPriceException extends BidException {
	public BidBelowStartPriceException(Price bidPrice, Price startPrice) {
		super(
			ErrorCode.BID_BELOW_START_PRICE,
			"입찰 가격이 시작 가격보다 낮습니다. "
				+ "bidPrice: " + bidPrice.getValue() + ", startPrice: " + startPrice.getValue()
		);
	}
}
