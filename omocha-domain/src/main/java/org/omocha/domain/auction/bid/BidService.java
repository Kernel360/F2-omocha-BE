package org.omocha.domain.auction.bid;

import java.util.List;

public interface BidService {

	public List<BidInfo.BidListResponse> getBidList(Long auctionId);

	public BidInfo.AddBidResponse addBid(BidCommand.AddBid addBid);

	public BidInfo.NowPriceResponse getNowPrice(Long auctionId);
}
