package org.omocha.domain.likes;

import java.time.LocalDateTime;

import org.omocha.domain.auction.AuctionCommand;

public interface LikeStore {
	void clickLike(AuctionCommand.LikeAuction likeCommand, LocalDateTime createdAt);

	void unClickLike(AuctionCommand.LikeAuction likeCommand);
}
