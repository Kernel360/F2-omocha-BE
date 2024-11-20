package org.omocha.domain.auction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionReader {
	Page<AuctionInfo.SearchAuction> getAuctionList(
		AuctionCommand.SearchAuction searchAuction,
		List<Long> subCategoryIds,
		Pageable pageable
	);

	Auction getAuction(Long auctionId);

	List<Auction> getExpiredBiddingAuctionList();

	Page<AuctionInfo.RetrieveMyAuctions> getMyAuctionList(
		AuctionCommand.RetrieveMyAuctions retrieveMyAuctions,
		Pageable pageable
	);

	Page<AuctionInfo.RetrieveMemberAuctions> getMemberAuctionList(
		AuctionCommand.RetrieveMemberAuctions retrieveMemberAuctions,
		Pageable pageable
	);

	Page<AuctionInfo.RetrieveMyBidAuctions> getMyBidAuctionList(Long memberId, Pageable sortPage);
}
