package org.omocha.domain.auction;

public interface AuctionStore {
	Auction store(Auction auction);

	void clickLike(AuctionCommand.LikeAuction likeCommand);

	void unClickLike(AuctionCommand.LikeAuction likeCommand);
}
