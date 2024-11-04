package org.omocha.api.application;

import java.util.List;

import org.omocha.domain.auction.bid.BidCommand;
import org.omocha.domain.auction.bid.BidInfo;
import org.omocha.domain.auction.bid.BidService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidFacade {

	private final BidService bidService;

	// TODO: Bid에서 GET의 경우 Auction이 없어도 불러와짐(AuctionId로만 조회하기에)
	//  상관 없지 않을까...? 검증이 필요할까?
	public List<BidInfo.BidList> retrieveBids(Long auctionId) {
		return bidService.retrieveBids(auctionId);
	}

	public BidInfo.AddBid addBid(BidCommand.AddBid addBid) {
		return bidService.addBid(addBid);
	}

	public BidInfo.NowPrice retrieveNowPrice(Long auctionId) {
		return bidService.retrieveNowPrice(auctionId);
	}
}