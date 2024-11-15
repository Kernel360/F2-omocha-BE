package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;

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
