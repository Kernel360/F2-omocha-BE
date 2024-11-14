package org.omocha.infra;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionStore;
import org.omocha.infra.repository.AuctionRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionStoreImpl implements AuctionStore {

	private final AuctionRepository auctionRepository;

	@Override
	public Auction store(Auction auction) {
		return auctionRepository.save(auction);
	}
}
