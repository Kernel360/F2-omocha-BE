package org.omocha.domain.auction.bid;

import java.util.List;
import java.util.Optional;

public interface BidReader {

	public List<Bid> getBidList(Long auctionId);

	public Optional<Bid> findHighestBid(Long auctionId);
}
