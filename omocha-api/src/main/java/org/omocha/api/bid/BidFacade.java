package org.omocha.api.bid;

import java.util.List;

import org.omocha.domain.bid.BidCommand;
import org.omocha.domain.bid.BidInfo;
import org.omocha.domain.bid.BidService;
import org.omocha.domain.notification.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidFacade {

	private final BidService bidService;
	private final NotificationService notificationService;

	public List<BidInfo.BidList> retrieveBids(Long auctionId) {
		return bidService.retrieveBids(auctionId);
	}

	public BidInfo.AddBid addBid(BidCommand.AddBid addBid) {
		BidInfo.AddBid result = bidService.addBid(addBid);
		notificationService.sendBidEvent(addBid.auctionId(), addBid.buyerMemberId());

		return result;
	}

	public BidInfo.NowPrice retrieveNowPrice(Long auctionId) {
		return bidService.retrieveNowPrice(auctionId);
	}

	public void buyNow(BidCommand.BuyNow buyNowCommand) {
		bidService.buyNow(buyNowCommand);
		notificationService.sendInstantBuyEvent(buyNowCommand.auctionId(), buyNowCommand.buyerMemberId());
	}

	public Page<BidInfo.RetrieveMyBids> retrieveMyBids(
		BidCommand.RetrieveMyBids retrieveMyBidsCommand,
		Pageable sortPage
	) {
		return bidService.retrieveMyBids(retrieveMyBidsCommand, sortPage);
	}
}