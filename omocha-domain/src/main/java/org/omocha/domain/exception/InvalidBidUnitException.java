package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class InvalidBidUnitException extends BidException {
	public InvalidBidUnitException(Long startPrice, Long bidUnit) {
		super(
			ErrorCode.INVALID_BID_UNIT,
			"입찰 금액이 입찰 단위에 유효하지 않습니다. startPrice: " + startPrice + ", bidUnit: " + bidUnit
		);
	}
}
