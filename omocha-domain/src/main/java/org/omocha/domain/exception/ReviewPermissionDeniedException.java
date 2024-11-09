package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class ReviewPermissionDeniedException extends BidException {
	public ReviewPermissionDeniedException(Long auctionId, Long reviewerId) {
		super(
			ErrorCode.REVIEW_PERMISSION_DENIED,
			"해당 경매에 리뷰를 작성할 권한이 없습니다. auctionId: " + auctionId + ", reviewerId: " + reviewerId
		);
	}
}
