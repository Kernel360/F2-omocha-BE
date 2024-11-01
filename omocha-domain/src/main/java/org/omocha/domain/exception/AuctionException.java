package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class AuctionException extends RuntimeException {
	private final ErrorCode erorrCode;
	private final String message;

	public AuctionException(ErrorCode erorrCode, String message) {
		super(message);
		this.erorrCode = erorrCode;
		this.message = message;
	}

}
