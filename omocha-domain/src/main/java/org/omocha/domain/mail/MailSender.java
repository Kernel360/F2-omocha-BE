package org.omocha.domain.mail;

public interface MailSender {

	void sendMail(MailCommand.SendMail sendCommand, String code);
}
