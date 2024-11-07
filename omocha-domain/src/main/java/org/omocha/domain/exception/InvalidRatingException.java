package org.omocha.domain.exception;

import org.omocha.domain.auction.review.Rating;
import org.omocha.domain.exception.code.ErrorCode;

public class InvalidRatingException extends BidException {
	public InvalidRatingException(Double value) {
		super(
			ErrorCode.INVALID_RATING,
			"리뷰 평점은 " + Rating.MIN + "점부터 " + Rating.MAX + "점까지만 가능합니다. value: " + value
		);
	}
}
