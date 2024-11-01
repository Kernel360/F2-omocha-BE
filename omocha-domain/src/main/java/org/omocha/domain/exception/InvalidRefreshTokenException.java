package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class InvalidRefreshTokenException extends JwtTokenException {
	public InvalidRefreshTokenException(String refreshToken) {
		super(
			ErrorCode.INVALID_REFRESH_TOKEN,
			"올바르지 않은 Refresh Token 입니다. Refresh Token = " + refreshToken
		);
	}
}
