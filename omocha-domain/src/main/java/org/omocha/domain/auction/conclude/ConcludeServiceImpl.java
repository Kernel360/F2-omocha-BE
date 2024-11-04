package org.omocha.domain.auction.conclude;

import java.util.List;
import java.util.Optional;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.auction.bid.Bid;
import org.omocha.domain.auction.bid.BidReader;
import org.omocha.domain.auction.chat.ChatCommand;
import org.omocha.domain.auction.chat.ChatService;
import org.omocha.domain.member.Member;
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
	private final ConcludeReader concludeReader;
	private final ChatService chatService;

	@Transactional
	public void concludeAuction() {
		List<Auction> expiredBiddingAuctions = auctionReader.findExpiredBiddingAuctions();

		for (Auction auction : expiredBiddingAuctions) {
			Optional<Bid> optionalHighestBid = bidReader.findHighestBid(auction.getAuctionId());

			optionalHighestBid.ifPresentOrElse(highestBid -> {
				concludeStore.store(auction, highestBid);

				Member highestBuyer = highestBid.getBuyer();

				var chatRoomCommand = new ChatCommand.CreateChatRoom(
					auction.getAuctionId(), highestBuyer.getMemberId(), highestBid.getBidPrice());
				chatService.addChatRoom(chatRoomCommand);

				auction.statusConcluded();
			}, () -> {
				auction.statusNoBids();
			});
		}
	}

	@Transactional(readOnly = true)
	public Long findConcludePrice(Long auctionId) {
		return concludeReader.findConclude(auctionId).getConcludePrice();
	}
}