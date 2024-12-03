package org.omocha.domain.mail.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MailCodeKeyNotFoundException extends MailException {
	public MailCodeKeyNotFoundException(String email, String requestCode) {
		super(ErrorCode.MAIL_CODE_KEY_NOT_FOUND,
			"유효하지않은 인증 코드입니다. email : " + email + ", requestCode : " + requestCode
		);
	}
}
