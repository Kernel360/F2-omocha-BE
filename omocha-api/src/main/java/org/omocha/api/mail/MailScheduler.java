package org.omocha.api.mail;

import org.omocha.domain.mail.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailScheduler {

	private final MailService mailService;

	@Scheduled(cron = "0 0 0 * * *")
	public void scheduleDeleteExpiredCode() {

		mailService.deleteCode();

	}

}
