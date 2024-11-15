package org.omocha.domain.auction.exception;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.common.code.ErrorCode;

public class AuctionNotInBiddingStateException extends AuctionException {
	public AuctionNotInBiddingStateException(Long auctionId, Auction.AuctionStatus auctionStatus) {
		super(
			ErrorCode.AUCTION_NOT_IN_BIDDING_STATUS,
			"경매의 상태가 입찰중이 아닙니다. auctionId: " + auctionId + ", auctionStatus: " + auctionStatus
		);
	}
}
