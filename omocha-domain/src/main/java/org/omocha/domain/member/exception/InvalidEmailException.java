package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidEmailException extends MemberException {
	public InvalidEmailException(String email) {
		super(
			ErrorCode.INVALID_EMAIL,
			"올바르지 않은 이메일 형식입니다. email: " + email
		);
	}
}
