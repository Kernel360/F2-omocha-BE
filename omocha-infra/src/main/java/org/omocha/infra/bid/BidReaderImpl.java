package org.omocha.infra.bid;

import java.util.List;
import java.util.Optional;

import org.omocha.domain.bid.Bid;
import org.omocha.domain.bid.BidCacheDto;
import org.omocha.domain.bid.BidInfo;
import org.omocha.domain.bid.BidReader;
import org.omocha.infra.bid.repository.BidCacheRepository;
import org.omocha.infra.bid.repository.BidRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BidReaderImpl implements BidReader {

	private final BidRepository bidRepository;
	private final BidCacheRepository bidCacheRepository;

	@Override
	public List<Bid> getBidList(Long auctionId) {
		return bidRepository.findAllByAuctionAuctionIdOrderByCreatedAtDesc(auctionId);
	}

	@Override
	public Optional<Bid> findHighestBid(Long auctionId) {
		return bidRepository.findTopByAuctionAuctionIdOrderByBidPriceDesc(auctionId);
	}

	@Override
	public Page<BidInfo.RetrieveMyBids> getMyBidList(Long memberId, Long auctionId, Pageable sortPage) {
		return bidRepository.getMyBidList(memberId, auctionId, sortPage);
	}

	@Override
	public BidCacheDto findNowPrice(Long auctionId) {
		return bidCacheRepository.findHighestBid(auctionId);
	}

	@Override
	public List<Long> getBuyerList(Long auctionId) {
		return bidRepository.findGroupedBuyerMemberIdsByAuctionId(auctionId);
	}
}
