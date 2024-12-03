package org.omocha.domain.mail.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MailAuthCodeMisMachException extends MailException {
	public MailAuthCodeMisMachException(String email, String requestCode, String authCode) {
		super(ErrorCode.MAIL_AUTH_CODE_MISMATCH,
			"코드가 일치하지 않습니다. email : " + email + ", requestCode : " + requestCode + ", authCode : " + authCode
		);
	}
}
