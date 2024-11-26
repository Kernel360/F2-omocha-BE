package org.omocha.api.mail.dto;

import org.omocha.domain.member.vo.Email;

public class MailDto {

	public record MailSendRequest(
		Email email
	) {

	}

	public record MailCodeVerification(
		Email email,
		String code
	) {

	}
}
