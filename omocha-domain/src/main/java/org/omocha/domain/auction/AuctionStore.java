package org.omocha.domain.auction;

public interface AuctionStore {
	Auction store(Auction auction);

	void removeAuction(Auction auction);
}
