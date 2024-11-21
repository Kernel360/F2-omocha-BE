package org.omocha.domain.auction.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

import lombok.Getter;

@Getter
public class AuctionException extends OmochaException {
	private final ErrorCode errorCode;
	private final String message;

	public AuctionException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		this.errorCode = errorCode;
		this.message = message;
	}
}
