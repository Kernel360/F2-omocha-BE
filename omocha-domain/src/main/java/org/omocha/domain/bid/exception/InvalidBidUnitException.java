package org.omocha.domain.bid.exception;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.common.code.ErrorCode;

public class InvalidBidUnitException extends BidException {
	public InvalidBidUnitException(Price startPrice, Price bidUnit) {
		super(
			ErrorCode.INVALID_BID_UNIT,
			"입찰 금액이 입찰 단위에 유효하지 않습니다. "
				+ "startPrice: " + startPrice.getValue() + ", bidUnit: " + bidUnit.getValue()
		);
	}
}
