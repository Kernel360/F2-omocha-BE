package org.omocha.domain.bid.exception;

import org.omocha.domain.common.code.ErrorCode;

public class BidNotExceedingCurrentHighestException extends BidException {
	public BidNotExceedingCurrentHighestException(Long bidPrice, Long currentHighestBidPrice) {
		super(
			ErrorCode.BID_BELOW_START_PRICE,
			"입찰 가격이 최고가보다 높지 않습니다. bidPrice: " + bidPrice + ", currentHighestBidPrice: " + currentHighestBidPrice
		);
	}
}
