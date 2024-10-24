package org.omocha.core.exception.jwt;

import org.omocha.client.common.code.JwtCode;

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
