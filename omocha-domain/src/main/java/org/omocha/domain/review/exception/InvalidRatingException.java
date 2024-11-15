package org.omocha.domain.review.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.review.rating.Rating;

public class InvalidRatingException extends ReviewException {
	public InvalidRatingException(Double value) {
		super(
			ErrorCode.INVALID_RATING,
			"리뷰 평점은 " + Rating.MIN + "점부터 " + Rating.MAX + "점까지만 가능합니다. value: " + value
		);
	}
}
