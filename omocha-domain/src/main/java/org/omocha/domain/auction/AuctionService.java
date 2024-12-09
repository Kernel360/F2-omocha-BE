package org.omocha.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionService {

	AuctionInfo.AddAuction addAuction(AuctionCommand.AddAuction addCommand);

	Page<AuctionInfo.SearchAuction> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	);

	AuctionInfo.RetrieveAuction retrieveAuction(AuctionCommand.RetrieveAuction retrieveCommand);

	void removeAuction(AuctionCommand.RemoveAuction removeCommand);

	AuctionInfo.LikeAuction likeAuction(AuctionCommand.LikeAuction likeCommand);

	Page<AuctionInfo.RetrieveMyAuctionLikes> retrieveMyAuctionLikes(
		Long memberId,
		Pageable pageable
	);

	Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctions(
		AuctionCommand.RetrieveMyAuctions retrieveMyAuctionsCommand,
		Pageable pageable
	);

	Page<AuctionInfo.RetrieveMyBidAuctions> retrieveMyBidAuctions(
		AuctionCommand.RetrieveMyBidAuctions retrieveMyBidAuctionsCommand, Pageable pageable);

	Page<AuctionInfo.RetrieveMemberAuctions> retrieveMemberAuctions(
		AuctionCommand.RetrieveMemberAuctions retrieveMemberAuctions,
		Pageable pageable
	);
}
