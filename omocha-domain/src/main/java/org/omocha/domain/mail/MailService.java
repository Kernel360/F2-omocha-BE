package org.omocha.domain.mail;

public interface MailService {

	void sendMail(MailCommand.SendMail sendCommand);

	Boolean verifyMailCode(MailCommand.VerifyMailCode verifyMailCodeCommand);

}
