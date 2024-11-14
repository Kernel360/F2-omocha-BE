package org.omocha.domain.auction.bid;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidReader {

	public List<Bid> getBidList(Long auctionId);

	public Optional<Bid> findHighestBid(Long auctionId);

	Page<BidInfo.RetrieveMyBids> getMyBidList(
		Long memberId,
		Long auctionId,
		Pageable sortPage
	);

}
