package org.auction.domain.bid.infrastructure.custom;

import org.auction.domain.bid.entity.BidEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidRepositoryCustom {
	Page<BidEntity> searchMyBidList(
		Long memberId,
		Pageable pageable
	);

}
