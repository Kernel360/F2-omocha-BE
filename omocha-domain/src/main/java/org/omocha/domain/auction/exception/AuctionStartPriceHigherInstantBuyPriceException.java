package org.omocha.domain.auction.exception;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.common.code.ErrorCode;

public class AuctionStartPriceHigherInstantBuyPriceException extends AuctionException {
	public AuctionStartPriceHigherInstantBuyPriceException(Price startPrice, Price instantBuyPrice) {
		super(ErrorCode.START_PRICE_HIGHER_THAN_INSTANT_BUY_PRICE,
			"시작 가격이 즉시 구매 가격보다 높을 수 없습니다. startPrice=" + startPrice.getValue() + ", instantBuyPrice="
				+ instantBuyPrice.getValue()
		);
	}
}
