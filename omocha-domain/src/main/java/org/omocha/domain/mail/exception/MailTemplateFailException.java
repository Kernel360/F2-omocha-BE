package org.omocha.domain.mail.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.member.vo.Email;

public class MailTemplateFailException extends MailException {
	public MailTemplateFailException(Email email) {
		super(ErrorCode.MAIL_TEMPLATE_FAILED,
			"메일 메세지 작성에 실패하였습니다. email : " + email.getValue()
		);
	}
}
