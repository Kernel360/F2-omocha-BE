package org.omocha.domain.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@EnableAsync
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final MailSender mailSender;

	@Value("{OMOCHA_EMAIL}")
	private String fromEmail;

	@Async
	@Override
	public void sendMail(MailCommand.SendMail sendCommand) {
		String code = CodeManager.addCode(sendCommand.email().getValue());

		mailSender.sendMail(sendCommand, code, fromEmail);

	}

	@Override
	public Boolean verifyMailCode(MailCommand.VerifyMailCode verifyMailCodeCommand) {
		return CodeManager.checkCode(verifyMailCodeCommand.email().getValue(), verifyMailCodeCommand.code());
	}

	@Override
	public void deleteCode() {

		CodeManager.removeCode();

	}

}
