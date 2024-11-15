package org.omocha.domain.bid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidService {

	List<BidInfo.BidList> retrieveBids(Long auctionId);

	BidInfo.AddBid addBid(BidCommand.AddBid addBid);

	BidInfo.NowPrice retrieveNowPrice(Long auctionId);

	void buyNow(BidCommand.BuyNow buyNowCommand);

	Page<BidInfo.RetrieveMyBids> retrieveMyBids(BidCommand.RetrieveMyBids retrieveMyBidsCommand, Pageable sortPage);
}
