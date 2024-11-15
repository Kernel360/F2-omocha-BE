package org.omocha.domain.auction.exception;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.common.code.ErrorCode;

public class AuctionNotConcludedException extends AuctionException {
	public AuctionNotConcludedException(Long auctionId, Auction.AuctionStatus auctionStatus) {
		super(
			ErrorCode.AUCTION_NOT_CONCLUDED,
			"경매가 낙찰되지 않은 상태입니다. auctionId: " + auctionId + ", auctionStatus: " + auctionStatus
		);
	}
}
