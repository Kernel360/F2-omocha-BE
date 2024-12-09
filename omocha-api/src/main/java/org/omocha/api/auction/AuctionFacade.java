package org.omocha.api.auction;

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

	public AuctionInfo.AddAuction addAuction(AuctionCommand.AddAuction addCommand) {
		return auctionService.addAuction(addCommand);
	}

	public Page<AuctionInfo.SearchAuction> searchAuctions(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	) {
		return auctionService.searchAuction(searchAuction, pageable);
	}

	public AuctionInfo.RetrieveAuction retrieveAuction(AuctionCommand.RetrieveAuction retrieveCommand) {
		return auctionService.retrieveAuction(retrieveCommand);
	}

	public void removeAuction(AuctionCommand.RemoveAuction removeCommand) {
		auctionService.removeAuction(removeCommand);
	}

	public AuctionInfo.LikeAuction likeAuction(AuctionCommand.LikeAuction likeCommand) {
		return auctionService.likeAuction(likeCommand);
	}

	public Page<AuctionInfo.RetrieveMyAuctionLikes> retrieveMyAuctionLikes(Long memberId, Pageable pageable) {
		return auctionService.retrieveMyAuctionLikes(memberId, pageable);
	}

	public Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctions(
		AuctionCommand.RetrieveMyAuctions retrieveMyAuctionsCommand,
		Pageable pageable
	) {
		return auctionService.retrieveMyAuctions(retrieveMyAuctionsCommand, pageable);
	}

	public Page<AuctionInfo.RetrieveMemberAuctions> retrieveMemberAuctions(
		AuctionCommand.RetrieveMemberAuctions retrieveMemberAuctions,
		Pageable pageable
	) {
		return auctionService.retrieveMemberAuctions(retrieveMemberAuctions, pageable);
	}

	public Page<AuctionInfo.RetrieveMyBidAuctions> retrieveMyBidAuctions(
		AuctionCommand.RetrieveMyBidAuctions retrieveMyBidAuctionsCommand,
		Pageable pageable
	) {
		return auctionService.retrieveMyBidAuctions(retrieveMyBidAuctionsCommand, pageable);
	}
}
