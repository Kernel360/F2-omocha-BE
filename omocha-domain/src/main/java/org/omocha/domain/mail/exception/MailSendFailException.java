package org.omocha.domain.mail.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.member.vo.Email;

public class MailSendFailException extends MailException {

	public MailSendFailException(Email email) {
		super(ErrorCode.MAIL_SEND_FAILED,
			"메일 전송에 실패했습니다. email : " + email
		);
	}
}
