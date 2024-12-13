package org.omocha.domain.mail;

import org.omocha.domain.member.MemberReader;
import org.omocha.domain.member.exception.MemberAlreadyExistException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final MailSender mailSender;
	private final CodeManager codeManager;
	private final MemberReader memberReader;

	@Override
	public void sendMail(MailCommand.SendMail sendCommand) {

		if (memberReader.existsByEmailAndProviderIsNull(sendCommand.email())) {
			throw new MemberAlreadyExistException(sendCommand.email());
		}

		String code = codeManager.addCode(sendCommand.email().getValue());

		mailSender.sendMail(sendCommand, code);

	}

	@Override
	public Boolean verifyMailCode(MailCommand.VerifyMailCode verifyMailCodeCommand) {
		return codeManager.checkCode(verifyMailCodeCommand.email().getValue(), verifyMailCodeCommand.code());
	}

}
