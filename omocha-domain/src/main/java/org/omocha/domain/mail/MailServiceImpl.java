package org.omocha.domain.mail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final MailSender mailSender;

	@Override
	public void sendMail(MailCommand.SendMail sendCommand) {
		String code = CodeManager.addCode(sendCommand.email().getValue());

		mailSender.sendMail(sendCommand, code);

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
