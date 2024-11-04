package org.omocha.domain.auction.bid;

import java.util.List;

public interface BidService {

	public List<BidInfo.BidList> retrieveBids(Long auctionId);

	public BidInfo.AddBid addBid(BidCommand.AddBid addBid);

	public BidInfo.NowPrice retrieveNowPrice(Long auctionId);
}
