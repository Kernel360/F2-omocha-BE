package org.omocha.domain.mail.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MailSendFailException extends MailException {

	public MailSendFailException(String email) {
		super(ErrorCode.MAIL_SEND_FAILED,
			"메일 전송에 실패했습니다." + email
		);
	}
}
