package org.omocha.domain.bid.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class BidException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;

	public BidException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
}