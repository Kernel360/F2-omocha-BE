package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidEmailFormatException extends MemberException {
	public InvalidEmailFormatException(String email) {
		super(
			ErrorCode.INVALID_EMAIL_FORMAT,
			"유효하지 않은 이메일 형식입니다. email: " + email
		);
	}
}
