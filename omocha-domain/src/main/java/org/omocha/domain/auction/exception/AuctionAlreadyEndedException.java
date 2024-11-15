package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

public class AuctionAlreadyEndedException extends AuctionException {
	public AuctionAlreadyEndedException(Long auctionId) {
		super(
			ErrorCode.AUCTION_ALREADY_ENDED,
			"이미 종료된 경매입니다. auctionId: " + auctionId
		);
	}
}