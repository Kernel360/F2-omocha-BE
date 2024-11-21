package org.omocha.domain.auction.exception;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.common.code.ErrorCode;

public class AuctionBidUnitTooHighException extends AuctionException {
	public AuctionBidUnitTooHighException(Price bidUnit, Long bidRange, Price startPrice, Price instantBuyPrice) {
		super(
			ErrorCode.BID_UNIT_TOO_HIGH,
			"입찰 단위가 너무 높습니다. bidUnit=" + bidUnit
				+ ", bidRange=" + bidRange
				+ ", startPrice=" + startPrice
				+ ", instantBuyPrice=" + instantBuyPrice
		);
	}
}
