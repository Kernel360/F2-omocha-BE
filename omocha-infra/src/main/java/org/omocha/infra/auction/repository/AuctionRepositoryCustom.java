package org.omocha.infra.auction.repository;

import java.util.List;

import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionRepositoryCustom {
	Page<AuctionInfo.SearchAuction> getAuctionList(
		AuctionCommand.SearchAuction searchAuction,
		List<Long> categoryIds,
		Pageable pageable
	);

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
