package org.omocha.infra.mail;

import org.omocha.domain.mail.CodeManager;
import org.omocha.domain.mail.MailCommand;
import org.omocha.domain.mail.MailService;
import org.omocha.domain.mail.exception.MailSendFailException;
import org.omocha.domain.member.vo.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@EnableAsync
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;

	@Value("{OMOCHA_EMAIL}")
	private String fromEmail;

	@Async
	@Override
	public void sendMail(MailCommand.SendMail sendCommand) {
		String code = CodeManager.addCode(sendCommand.email().getValue());

		MimeMessage mail = createMimeMessage(sendCommand.email(), code);
		mailSender.send(mail);
	}

	@Override
	public Boolean verifyMailCode(MailCommand.VerifyMailCode verifyMailCodeCommand) {
		return CodeManager.checkCode(verifyMailCodeCommand.email().getValue(), verifyMailCodeCommand.code());
	}

	public MimeMessage createMimeMessage(Email email, String code) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			mimeMessage.setFrom(fromEmail);
			mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email.getValue());
			mimeMessage.setSubject("이메일 인증");
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

	@Override
	public void deleteCode() {

		CodeManager.removeCode();

	}

}
