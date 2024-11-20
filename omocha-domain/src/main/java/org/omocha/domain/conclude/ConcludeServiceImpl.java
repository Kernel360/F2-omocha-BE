package org.omocha.domain.conclude;

import java.util.List;
import java.util.Optional;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.bid.Bid;
import org.omocha.domain.bid.BidReader;
import org.omocha.domain.chat.ChatCommand;
import org.omocha.domain.chat.ChatService;
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

	@Override
	@Transactional
	public void concludeAuction() {
		List<Auction> expiredBiddingAuctions = auctionReader.getExpiredBiddingAuctionList();

		for (Auction auction : expiredBiddingAuctions) {
			Optional<Bid> optionalHighestBid = bidReader.findHighestBid(auction.getAuctionId());

			optionalHighestBid.ifPresentOrElse(highestBid -> {
				concludeStore.store(auction, highestBid);

				Member highestBuyer = highestBid.getBuyer();

				var chatRoomCommand = new ChatCommand.AddChatRoom(
					auction.getAuctionId(), highestBuyer.getMemberId());
				chatService.addChatRoom(chatRoomCommand);

				auction.statusConcluded();
			}, () -> {
				auction.statusNoBids();
			});
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Price findConcludePrice(Long auctionId) {
		return concludeReader.getConclude(auctionId).getConcludePrice();
	}
}