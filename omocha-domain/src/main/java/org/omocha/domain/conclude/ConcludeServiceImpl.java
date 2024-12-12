package org.omocha.domain.conclude;

import java.util.List;
import java.util.Optional;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.bid.Bid;
import org.omocha.domain.bid.BidReader;
import org.omocha.domain.chat.ChatCommand;
import org.omocha.domain.chat.ChatService;
import org.omocha.domain.member.Member;
import org.omocha.domain.notification.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcludeServiceImpl implements ConcludeService {

	private final AuctionReader auctionReader;
	private final BidReader bidReader;
	private final ConcludeStore concludeStore;
	private final ChatService chatService;
	private final NotificationService notificationService;

	@Override
	@Transactional
	public void concludeAuction() {
		List<Auction> expiredBiddingAuctions = auctionReader.getExpiredBiddingAuctionList();

		for (Auction auction : expiredBiddingAuctions) {
			Optional<Bid> optionalHighestBid = bidReader.findHighestBid(auction.getAuctionId());

			optionalHighestBid.ifPresentOrElse(highestBid -> {
				concludeStore.store(auction, highestBid);

				Member highestBuyer = highestBid.getBuyer();

				auction.statusConcluded();

				notificationService.sendConcludeEvent(
					auction.getAuctionId(),
					auction.getMemberId(),
					highestBuyer.getMemberId()
				);

				var chatRoomCommand = new ChatCommand.AddChatRoom(
					auction.getAuctionId(), highestBuyer.getMemberId());
				chatService.addChatRoom(chatRoomCommand);
			}, () -> {
				auction.statusNoBids();

				notificationService.sendConcludeEvent(
					auction.getAuctionId(),
					auction.getMemberId(),
					null
				);
			});
		}
	}
}