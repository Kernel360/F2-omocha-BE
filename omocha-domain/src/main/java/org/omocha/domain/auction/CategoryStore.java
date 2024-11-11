package org.omocha.domain.auction;

public interface CategoryStore {
	Category store(Category category);

	void store(Auction auction, AuctionCommand.AddAuction addCommand);
}
