package org.omocha.api.bid;

import org.omocha.domain.conclude.ConcludeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcludeScheduler {

	private final ConcludeService concludeService;

	// TODO: 추후 Redis로 낙찰 로직 처리하도록 변경(현재는 임시용)

	@Scheduled(cron = "0 * * * * *")
	public void scheduleAuctionConclusions() {
		concludeService.concludeAuction();
	}
}