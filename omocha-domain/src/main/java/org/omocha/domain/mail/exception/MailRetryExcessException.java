package org.omocha.domain.mail.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MailRetryExcessException extends MailException {
	public MailRetryExcessException() {
		super(ErrorCode.MAIL_RETRY_EXCESS,
			"짧은 재시도 에러입니다."
		);
	}
}
