package org.omocha.infra.bid;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.bid.Bid;
import org.omocha.domain.bid.BidStore;
import org.omocha.domain.member.Member;
import org.omocha.infra.bid.repository.BidCacheRepository;
import org.omocha.infra.bid.repository.BidRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BidStoreImpl implements BidStore {

	private final BidRepository bidRepository;
	private final BidCacheRepository bidCacheRepository;

	@Override
	public Bid store(Auction auction, Member buyer, Price bidPrice) {

		Bid savedBid = bidRepository.save(
			Bid.builder()
				.auction(auction)
				.buyer(buyer)
				.bidPrice(bidPrice)
				.build()
		);

		auction.updateNowPrice(bidPrice);

		bidCacheRepository.storeHighestBid(auction.getAuctionId(), savedBid);

		return savedBid;
	}

}
