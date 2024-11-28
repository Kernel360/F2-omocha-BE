package org.omocha.api.mail.dto;

public class MailDto {

	public record MailSendRequest(
		String email
	) {

	}

	public record MailCodeVerificationRequest(
		String email,
		String code
	) {

	}
}
