package org.omocha.domain.bid;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HighestBidManager {

	private final BidReader bidReader;

	public BidCacheDto getHighestBid(Long auctionId) {
		return bidReader.findNowPrice(auctionId);
	}

	public Optional<BidCacheDto> getCurrentHighestBid(Long auctionId) {
		return Optional.ofNullable(getHighestBid(auctionId))
			.or(() -> bidReader.findHighestBid(auctionId)
				.map(BidCacheDto::toRedis));
	}
}