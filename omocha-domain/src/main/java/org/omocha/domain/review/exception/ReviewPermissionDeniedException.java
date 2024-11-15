package org.omocha.domain.review.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ReviewPermissionDeniedException extends ReviewException {
	public ReviewPermissionDeniedException(Long auctionId, Long reviewerId) {
		super(
			ErrorCode.REVIEW_PERMISSION_DENIED,
			"해당 경매에 리뷰를 작성할 권한이 없습니다. auctionId: " + auctionId + ", reviewerId: " + reviewerId
		);
	}
}
