package org.omocha.domain.mail;

import org.omocha.domain.member.vo.Email;

public class MailCommand {

	public record SendMail(
		Email email
	) {

	}

	public record VerifyMailCode(
		Email email,
		String code
	) {

	}

}
