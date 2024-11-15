package org.omocha.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionService {

	Long addAuction(AuctionCommand.AddAuction addCommand);

	Page<AuctionInfo.SearchAuction> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	);

	AuctionInfo.RetrieveAuction retrieveAuction(AuctionCommand.RetrieveAuction retrieveCommand);

	void removeAuction(AuctionCommand.RemoveAuction removeCommand);

	Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctions(
		AuctionCommand.RetrieveMyAuctions retrieveMyAuctionsCommand,
		Pageable pageable
	);

	Page<AuctionInfo.RetrieveMyBidAuctions> retrieveMyBidAuctions(
		AuctionCommand.RetrieveMyBidAuctions retrieveMyBidAuctionsCommand, Pageable pageable);
}
