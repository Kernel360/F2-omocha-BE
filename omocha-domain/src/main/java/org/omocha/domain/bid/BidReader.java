package org.omocha.domain.bid;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidReader {

	List<Bid> getBidList(Long auctionId);

	Optional<Bid> findHighestBid(Long auctionId);

	Page<BidInfo.RetrieveMyBids> getMyBidList(
		Long memberId,
		Long auctionId,
		Pageable sortPage
	);

	BidCacheDto findNowPrice(Long auctionId);

	List<Long> getBuyerList(Long auctionId);
}
