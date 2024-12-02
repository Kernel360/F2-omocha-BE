package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidTokenOwnerException extends MemberException {
	public InvalidTokenOwnerException(Long memberId, String refreshToken) {
		super(
			ErrorCode.INVALID_EMAIL_FORMAT,
			"Refresh Token의 소유자가 일치하지 않습니다. " + "memberId: " + memberId + ", refreshToken: " + refreshToken
		);
	}
}
