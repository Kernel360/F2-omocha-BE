package org.omocha.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeReader {
	boolean getAuctionLikeStatus(AuctionCommand.LikeAuction likeCommand);

	Page<AuctionInfo.RetrieveMyAuctionLikes> getMyAuctionLikes(
		Long memberId,
		Pageable pageable
	);
}
