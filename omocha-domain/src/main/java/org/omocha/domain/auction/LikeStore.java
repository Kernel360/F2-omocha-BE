package org.omocha.domain.auction;

import java.time.LocalDateTime;

public interface LikeStore {
	void clickLike(AuctionCommand.LikeAuction likeCommand, LocalDateTime createdAt);

	void unClickLike(AuctionCommand.LikeAuction likeCommand);
}
