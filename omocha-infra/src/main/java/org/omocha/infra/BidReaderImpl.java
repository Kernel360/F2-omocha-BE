package org.omocha.infra;

import java.util.List;
import java.util.Optional;

import org.omocha.domain.auction.bid.Bid;
import org.omocha.domain.auction.bid.BidReader;
import org.omocha.infra.repository.BidRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BidReaderImpl implements BidReader {

	private final BidRepository bidRepository;

	@Override
	public List<Bid> getBidList(Long auctionId) {
		return bidRepository.findAllByAuctionAuctionIdOrderByCreatedAtDesc(auctionId);
	}

	@Override
	public Optional<Bid> findHighestBid(Long auctionId) {
		return bidRepository.findTopByAuctionAuctionIdOrderByBidPriceDesc(auctionId);
	}
}
