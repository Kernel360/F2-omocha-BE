package org.omocha.domain.auction.bid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidService {

	public List<BidInfo.BidList> retrieveBids(Long auctionId);

	public BidInfo.AddBid addBid(BidCommand.AddBid addBid);

	public BidInfo.NowPrice retrieveNowPrice(Long auctionId);

	Page<BidInfo.RetrieveMyBids> retrieveMyBids(BidCommand.RetrieveMyBids retrieveMyBidsCommand, Pageable sortPage);
}
