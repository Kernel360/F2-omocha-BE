package org.omocha.api.mail;

import org.omocha.domain.mail.MailCommand;
import org.omocha.domain.mail.MailService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailFacade {

	private final MailService mailService;

	public void sendMail(MailCommand.SendMail sendCommand) {
		mailService.sendMail(sendCommand);
	}

	public Boolean verifyMailCode(MailCommand.VerifyMailCode verifyMailCodeCommand) {
		return mailService.verifyMailCode(verifyMailCodeCommand);
	}
}
