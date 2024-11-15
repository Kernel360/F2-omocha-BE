package org.omocha.infra.bid.repository;

import org.omocha.domain.bid.BidInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidRepositoryCustom {

	Page<BidInfo.RetrieveMyBids> getMyBidList(
		Long memberId,
		Long auctionId,
		Pageable sortPage
	);

}