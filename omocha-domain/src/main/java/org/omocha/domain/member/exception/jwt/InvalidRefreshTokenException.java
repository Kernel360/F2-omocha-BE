package org.omocha.domain.member.exception.jwt;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidRefreshTokenException extends JwtTokenException {
	public InvalidRefreshTokenException(String refreshToken) {
		super(
			ErrorCode.INVALID_REFRESH_TOKEN,
			"올바르지 않은 Refresh Token 입니다. Refresh Token = " + refreshToken
		);
	}
}
