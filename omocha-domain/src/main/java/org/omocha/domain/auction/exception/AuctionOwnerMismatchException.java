package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

public class AuctionOwnerMismatchException extends AuctionException {
	public AuctionOwnerMismatchException(Long memberId) {
		super(
			ErrorCode.AUCTION_MEMBER_INVALID,
			"경매 작성자와 일치하지 않는 사용자입니다. memberId: " + memberId
		);
	}
}
