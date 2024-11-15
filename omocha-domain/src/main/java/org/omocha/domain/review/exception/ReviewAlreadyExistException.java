package org.omocha.domain.review.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ReviewAlreadyExistException extends ReviewException {
	public ReviewAlreadyExistException(Long auctionId, Long reviewerId) {
		super(
			ErrorCode.REVIEW_ALREADY_EXIST,
			"이미 리뷰를 작성하였습니다. auctionId: " + auctionId + ", reviewerId: " + reviewerId
		);
	}
}
