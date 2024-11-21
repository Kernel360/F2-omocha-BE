package org.omocha.domain.common.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class OmochaException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;

	public OmochaException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
}