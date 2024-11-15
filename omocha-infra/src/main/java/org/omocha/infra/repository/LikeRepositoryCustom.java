package org.omocha.infra.repository;

import org.omocha.domain.auction.AuctionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeRepositoryCustom {

	Page<AuctionInfo.RetrieveMyAuctionLikes> getMyAuctionLikes(Long memberId, Pageable pageable);
}
