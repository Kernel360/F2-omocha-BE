package org.omocha.infra.auction;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.auction.exception.AuctionNotFoundException;
import org.omocha.infra.auction.repository.AuctionRepository;
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
	public Page<AuctionInfo.SearchAuction> getAuctionList(
		AuctionCommand.SearchAuction searchAuction,
		List<Long> subCategoryIds,
		Pageable pageable
	) {
		return auctionRepository.getAuctionList(searchAuction, subCategoryIds, pageable);
	}

	@Override
	public Auction getAuction(Long auctionId) {
		return auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(auctionId));
	}

	@Override
	public List<Auction> getExpiredBiddingAuctionList() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneHourBefore = now.minusHours(1);

		return auctionRepository.findAllByAuctionStatusAndEndDateBetween(
			Auction.AuctionStatus.BIDDING,
			oneHourBefore,
			now
		);
	}

	@Override
	public Page<AuctionInfo.RetrieveMyAuctions> getMyAuctionList(
		AuctionCommand.RetrieveMyAuctions retrieveMyAuctions,
		Pageable pageable
	) {
		return auctionRepository.getMyAuctionList(retrieveMyAuctions, pageable);
	}

	@Override
	public Page<AuctionInfo.RetrieveMemberAuctions> getMemberAuctionList(
		AuctionCommand.RetrieveMemberAuctions retrieveMemberAuctions,
		Pageable pageable
	) {
		return auctionRepository.getMemberAuctionList(retrieveMemberAuctions, pageable);
	}

	@Override
	public Page<AuctionInfo.RetrieveMyBidAuctions> getMyBidAuctionList(Long memberId, Pageable sortPage) {
		return auctionRepository.getMyBidAuctionList(memberId, sortPage);
	}

}
