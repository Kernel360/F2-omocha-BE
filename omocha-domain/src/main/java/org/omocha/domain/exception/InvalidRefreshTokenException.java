package org.omocha.domain.exception;

import org.omocha.domain.exception.code.JwtCode;

public class InvalidRefreshTokenException extends JwtTokenException {
	public InvalidRefreshTokenException(
		JwtCode jwtCode
	) {
		super(jwtCode);
	}

	public InvalidRefreshTokenException(
		JwtCode jwtCode,
		String detailMessage
	) {
		super(jwtCode, detailMessage);
	}
}
