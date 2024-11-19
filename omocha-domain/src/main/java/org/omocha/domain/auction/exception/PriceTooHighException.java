package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

public class PriceTooHighException extends AuctionException {
	public PriceTooHighException(long price) {
		super(
			ErrorCode.PRICE_TOO_HIGH,
			"허용 가능한 최대 금액을 초과했습니다. price: " + price
		);
	}
}