package org.omocha.domain.conclude;

import org.omocha.domain.auction.vo.Price;

public interface ConcludeService {
	public void concludeAuction();

	public Price findConcludePrice(Long auctionId);
}
