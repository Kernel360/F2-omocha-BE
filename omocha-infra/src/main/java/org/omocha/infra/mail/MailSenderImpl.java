package org.omocha.infra.mail;

import org.omocha.domain.mail.MailCommand;
import org.omocha.domain.mail.MailSender;
import org.omocha.domain.mail.exception.MailSendFailException;
import org.omocha.infra.mail.template.MailTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

	private final JavaMailSender mailSender;
	private final MailTemplate mailTemplate;

	@Async("mailExecutor")
	@Override
	public void sendMail(MailCommand.SendMail sendCommand, String code) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessage mail = mailTemplate.getMimeMessage("authCodeTemplate", sendCommand.email(), code, mimeMessage);
		try {
			mailSender.send(mail);
		} catch (MailException e) {
			MailSendFailException msfe = new MailSendFailException(sendCommand.email());
			msfe.initCause(e);
			throw msfe;
		}

	}

}
