package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class AuctionNotFoundException extends AuctionException {
	public AuctionNotFoundException(Long auctionId) {
		super(
			ErrorCode.AUCTION_NOT_FOUND,
			"경매를 찾을 수 없습니다. auctionId: {} " + auctionId
		);
	}
}
