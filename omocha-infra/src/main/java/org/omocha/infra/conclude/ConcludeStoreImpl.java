package org.omocha.infra.conclude;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.bid.Bid;
import org.omocha.domain.conclude.Conclude;
import org.omocha.domain.conclude.ConcludeStore;
import org.omocha.infra.conclude.repository.ConcludeRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConcludeStoreImpl implements ConcludeStore {

	private final ConcludeRepository concludeRepository;

	@Override
	public Conclude store(Auction auction, Bid highestBid) {

		Conclude conclude = Conclude.builder()
			.concludePrice(highestBid.getBidPrice())
			.concludedAt(highestBid.getCreatedAt())
			.auction(auction)
			.buyer(highestBid.getBuyer())
			.build();

		return concludeRepository.save(conclude);
	}
}
