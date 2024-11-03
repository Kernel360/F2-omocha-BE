package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;

	public JwtTokenException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
}
