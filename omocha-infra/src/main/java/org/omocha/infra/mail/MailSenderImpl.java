package org.omocha.infra.mail;

import org.omocha.domain.mail.MailCommand;
import org.omocha.domain.mail.MailSender;
import org.omocha.domain.mail.exception.MailSendFailException;
import org.omocha.domain.member.vo.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@EnableAsync
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

	private final JavaMailSender mailSender;

	@Value("{OMOCHA_EMAIL}")
	private String fromEmail;

	@Async
	@Override
	public void sendMail(MailCommand.SendMail sendCommand, String code) {

		MimeMessage mail = createMimeMessage(sendCommand.email(), code);
		mailSender.send(mail);
	}

	private MimeMessage createMimeMessage(Email email, String code) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			mimeMessage.setFrom(fromEmail);
			mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email.getValue());
			mimeMessage.setSubject("Omocha 이메일 인증");
			String body = "";
			body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
			body += "<h1>" + code + "</h1>";
			body += "<h3>" + "감사합니다." + "</h3>";
			mimeMessage.setText(body, "UTF-8", "html");
		} catch (MessagingException e) {
			throw new MailSendFailException(email.getValue());
		}

		return mimeMessage;
	}
}
