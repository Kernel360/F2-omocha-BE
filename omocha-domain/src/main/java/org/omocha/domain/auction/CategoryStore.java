package org.omocha.domain.auction;

public interface CategoryStore {
	Category categoryStore(Category category);

	void auctionCategoryStore(Auction auction, AuctionCommand.AddAuction addCommand);
}
