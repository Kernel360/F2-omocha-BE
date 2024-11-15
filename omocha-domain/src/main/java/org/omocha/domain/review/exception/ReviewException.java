package org.omocha.domain.review.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {
	private final ErrorCode bidCode;
	private final String message;

	public ReviewException(ErrorCode bidCode, String message) {
		super(message);
		this.bidCode = bidCode;
		this.message = message;
	}
}