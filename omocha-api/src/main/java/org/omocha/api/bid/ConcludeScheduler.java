package org.omocha.api.bid;

import java.util.List;

import org.omocha.domain.conclude.ConcludeService;
import org.omocha.domain.notification.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcludeScheduler {

	private final ConcludeService concludeService;
	private final NotificationService notificationService;

	@Scheduled(cron = "0 * * * * *")
	public void scheduleAuctionConclusions() {
		List<Long> concludedAuctionIdList = concludeService.concludeAuction();

		notificationService.sendConcludeEvent(concludedAuctionIdList);
	}
}