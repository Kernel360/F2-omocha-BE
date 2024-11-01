package org.omocha.infra;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.bid.Bid;
import org.omocha.domain.auction.bid.BidStore;
import org.omocha.domain.member.Member;
import org.omocha.infra.repository.BidRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BidStoreImpl implements BidStore {

	private final BidRepository bidRepository;

	@Override
	public Bid store(Auction auction, Member buyer, Long bidPrice) {

		Bid savedBid = bidRepository.save(
			Bid.builder()
				.auction(auction)
				.buyer(buyer)
				.bidPrice(bidPrice)
				.build()
		);

		auction.updateNowPrice(bidPrice);

		return savedBid;
	}
}
