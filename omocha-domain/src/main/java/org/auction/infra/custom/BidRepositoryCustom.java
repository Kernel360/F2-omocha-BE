package org.auction.infra.custom;

import org.auction.domain.bid.BidEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidRepositoryCustom {
	Page<BidEntity> searchMyBidList(
		Long memberId,
		Pageable pageable
	);

}
