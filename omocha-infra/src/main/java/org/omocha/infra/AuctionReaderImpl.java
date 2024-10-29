package org.omocha.infra;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionReader;
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
	public Page<Auction> searchAuctionList(AuctionCommand.SearchAuction searchAuction, Pageable pageable) {
		return auctionRepository.searchAuctionList(searchAuction, pageable);
	}

	@Override
	public Auction findByAuctionId(Long auctionId) {
		return auctionRepository.findById(auctionId)
			.orElseThrow(RuntimeException::new);
	}
}
