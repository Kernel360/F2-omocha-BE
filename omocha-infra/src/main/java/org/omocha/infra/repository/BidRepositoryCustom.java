package org.omocha.infra.repository;

import org.omocha.domain.auction.bid.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidRepositoryCustom {
	Page<Bid> searchMyBidList(Long memberId, Pageable pageable);
}