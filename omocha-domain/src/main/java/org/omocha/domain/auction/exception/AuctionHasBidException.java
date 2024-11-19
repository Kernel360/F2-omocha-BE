package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

public class AuctionHasBidException extends AuctionException {
	public AuctionHasBidException(Long auctionId) {
		super(
			ErrorCode.AUCTION_HAS_BIDS,
			"Auction에 입찰이 걸려있어서 삭제할 수 없습니다. AuctionId: " + auctionId
		);
	}
}
