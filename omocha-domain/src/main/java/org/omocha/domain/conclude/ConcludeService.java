package org.omocha.domain.conclude;

public interface ConcludeService {
	public void concludeAuction();

	public Long findConcludePrice(Long auctionId);
}
