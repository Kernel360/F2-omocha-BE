package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class InvalidPhoneNumberFormatException extends MemberException {
	public InvalidPhoneNumberFormatException(String email) {
		super(
			ErrorCode.INVALID_PHONE_NUMBER_FORMAT,
			"유효하지 않은 휴대폰번호 형식입니다. email: " + email
		);
	}
}
