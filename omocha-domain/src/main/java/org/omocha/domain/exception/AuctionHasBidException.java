package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class AuctionHasBidException extends AuctionException {
	public AuctionHasBidException(Long auctionId) {
		super(
			ErrorCode.AUCTION_HAS_BIDS,
			"Auction에 입찰이 걸려있어서 삭제할 수 없습니다. AuctionId: {}" + auctionId
		);
	}
}
