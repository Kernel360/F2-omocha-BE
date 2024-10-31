package org.auction.client.bid.application;

import java.util.List;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcludeScheduler {

	private final ConcludeService concludeService;

	@Scheduled(cron = "0 * * * * *")
	public void scheduleAuctionConclusions() {
		List<AuctionEntity> biddingAuctions = concludeService.findBiddingAuctions();

		for (AuctionEntity auction : biddingAuctions) {
			concludeService.concludeAuctionIfEnded(auction);
		}
	}
}