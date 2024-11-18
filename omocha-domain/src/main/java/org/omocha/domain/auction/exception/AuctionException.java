package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class AuctionException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;

	public AuctionException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

}
