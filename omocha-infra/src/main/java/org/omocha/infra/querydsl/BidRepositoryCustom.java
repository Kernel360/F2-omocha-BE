package org.omocha.infra.querydsl;

import org.omocha.domain.bid.BidEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidRepositoryCustom {
	Page<BidEntity> searchMyBidList(
		Long memberId,
		Pageable pageable
	);

}
