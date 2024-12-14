package org.omocha.infra.mail;

import org.omocha.domain.mail.MailCommand;
import org.omocha.domain.mail.MailSender;
import org.omocha.infra.mail.template.MailTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@EnableAsync
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

	private final JavaMailSender mailSender;
	private final MailTemplate mailTemplate;

	@Async
	@Override
	public void sendMail(MailCommand.SendMail sendCommand, String code) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessage mail = mailTemplate.getMimeMessage("authCodeTemplate", sendCommand.email(), code, mimeMessage);
		mailSender.send(mail);
	}

}
