package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidRefreshTokenException extends MemberException {
	public InvalidRefreshTokenException(String refreshToken) {
		super(
			ErrorCode.INVALID_REFRESH_TOKEN,
			"올바르지 않는 Refresh Token 입니다. refreshToken: " + refreshToken
		);
	}
}
