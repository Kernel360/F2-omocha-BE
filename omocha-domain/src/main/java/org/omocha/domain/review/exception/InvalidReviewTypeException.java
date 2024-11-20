package org.omocha.domain.review.exception;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidReviewTypeException extends ReviewException {
	public InvalidReviewTypeException(String reviewType) {
		super(
			ErrorCode.REVIEW_ALREADY_EXIST,
			"유효하지 않은 리뷰 타입입니다. reviewType: " + reviewType
		);
	}
}
