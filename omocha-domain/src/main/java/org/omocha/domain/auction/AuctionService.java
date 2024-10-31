package org.omocha.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionService {

	Long registerAuction(AuctionCommand.RegisterAuction registerAuction);

	Page<AuctionInfo.AuctionListResponse> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	);

	AuctionInfo.AuctionDetailResponse retrieveAuctionDetail(AuctionCommand.RetrieveAuction retrieveAuction);
}
