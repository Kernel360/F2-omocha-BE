package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

public class AuctionNotFoundException extends AuctionException {
	public AuctionNotFoundException(Long auctionId) {
		super(
			ErrorCode.AUCTION_NOT_FOUND,
			"경매를 찾을 수 없습니다. auctionId: " + auctionId
		);
	}
}
