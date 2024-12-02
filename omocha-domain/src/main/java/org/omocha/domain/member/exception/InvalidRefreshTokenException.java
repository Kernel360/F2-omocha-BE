package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidRefreshTokenException extends MemberException {
	public InvalidRefreshTokenException(String refreshToken) {
		super(
			ErrorCode.INVALID_EMAIL_FORMAT,
			"유효하지 Refresh Token입니다. refreshToken: " + refreshToken
		);
	}
}
