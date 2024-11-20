package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

public class NegativePriceException extends AuctionException {
	public NegativePriceException(long price) {
		super(
			ErrorCode.NEGATIVE_PRICE,
			"금액은 음수일 수 없습니다. price: " + price
		);
	}
}