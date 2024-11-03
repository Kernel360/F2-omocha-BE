package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class BidException extends RuntimeException {
	private final ErrorCode bidCode;
	private final String message;

	public BidException(ErrorCode bidCode, String message) {
		super(message);
		this.bidCode = bidCode;
		this.message = message;
	}
}