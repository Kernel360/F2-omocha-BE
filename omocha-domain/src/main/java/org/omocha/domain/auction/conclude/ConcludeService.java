package org.omocha.domain.auction.conclude;

public interface ConcludeService {
	public void concludeAuction();

	public Long findConcludePrice(Long auctionId);
}
