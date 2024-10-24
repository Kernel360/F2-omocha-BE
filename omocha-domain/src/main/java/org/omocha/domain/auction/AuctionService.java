package org.omocha.domain.auction;

public interface AuctionService {

	Long registerAuction(AuctionCommand.RegisterAuction registerAuction);

	/*Page<AuctionInfo.Main> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	);*/
}
