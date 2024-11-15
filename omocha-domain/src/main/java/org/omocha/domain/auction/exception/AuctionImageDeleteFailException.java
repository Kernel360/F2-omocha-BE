package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

public class AuctionImageDeleteFailException extends AuctionException {
	public AuctionImageDeleteFailException(Long auctionId) {
		super(
			ErrorCode.AUCTION_IMAGE_DELETE_FAIL,
			"Auction 이미지를 삭제할 수 없습니다. auctionId: {} " + auctionId
		);
	}
}
