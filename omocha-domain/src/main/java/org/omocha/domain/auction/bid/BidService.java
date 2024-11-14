package org.omocha.domain.auction.bid;

import java.util.List;

public interface BidService {

	List<BidInfo.BidList> retrieveBids(Long auctionId);

	BidInfo.AddBid addBid(BidCommand.AddBid addBid);

	BidInfo.NowPrice retrieveNowPrice(Long auctionId);

	void buyNow(BidCommand.BuyNow buyNowCommand);
}
