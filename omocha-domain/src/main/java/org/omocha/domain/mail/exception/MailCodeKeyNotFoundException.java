package org.omocha.domain.mail.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MailCodeKeyNotFoundException extends MailException {
	public MailCodeKeyNotFoundException(String email) {
		super(ErrorCode.MAIL_CODE_KEY_NOT_FOUND,
			"키 값을 찾을 수 없습니다. email : " + email
		);
	}
}
