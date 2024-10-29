package org.omocha.api.application;

import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.AuctionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionFacade {

	private final AuctionService auctionService;

	public Long addAuction(AuctionCommand.RegisterAuction registerAuction) {
		Long auctionId = auctionService.registerAuction(registerAuction);
		return auctionId;
	}

	public Page<AuctionInfo.AuctionListResponse> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	) {
		return auctionService.searchAuction(searchAuction, pageable);
	}

	public AuctionInfo.AuctionDetailResponse findAuctionDetail(AuctionCommand.RetrieveAuction retrieveAuctionId) {
		return auctionService.retrieveAuctionDetail(retrieveAuctionId);
	}

}
