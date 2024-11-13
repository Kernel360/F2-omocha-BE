package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class ReviewAlreadyExistException extends BidException {
	public ReviewAlreadyExistException(Long auctionId, Long reviewerId) {
		super(
			ErrorCode.REVIEW_ALREADY_EXIST,
			"이미 리뷰를 작성하였습니다. auctionId: " + auctionId + ", reviewerId: " + reviewerId
		);
	}
}
