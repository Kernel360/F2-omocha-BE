package org.omocha.infra;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.exception.AuctionNotFoundException;
import org.omocha.infra.repository.AuctionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionReaderImpl implements AuctionReader {

	private final AuctionRepository auctionRepository;

	@Override
	public Page<AuctionInfo.AuctionListResponse> searchAuctionList(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	) {
		return auctionRepository.searchAuctionList(searchAuction, pageable);
	}

	@Override
	public Auction getAuction(Long auctionId) {
		return auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(auctionId));
	}

	@Override
	public List<Auction> findExpiredBiddingAuctions() {
		return auctionRepository.findAllByAuctionStatusAndEndDateBefore(
			Auction.AuctionStatus.BIDDING, LocalDateTime.now());
	}

	// @Override
	// public Page<Auction> searchMyAuctionList(Long memberId, AuctionStatus auctionStatus, Pageable pageable) {
	// 	return auctionRepository.searchMyAuctionList(memberId, auctionStatus, pageable);
	// }

}
