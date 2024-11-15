package org.omocha.domain.category;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;

public interface CategoryStore {
	Category categoryStore(Category category);

	void auctionCategoryStore(Auction auction, AuctionCommand.AddAuction addCommand);
}
