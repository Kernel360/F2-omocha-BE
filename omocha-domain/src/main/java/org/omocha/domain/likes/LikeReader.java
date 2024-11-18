package org.omocha.domain.likes;

import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeReader {
	boolean getAuctionLikeStatus(AuctionCommand.LikeAuction likeCommand);

	Page<AuctionInfo.RetrieveMyAuctionLikes> getMyAuctionLikes(
		Long memberId,
		Pageable pageable
	);

	int getMemberLikeCount(Long memberId);
}
