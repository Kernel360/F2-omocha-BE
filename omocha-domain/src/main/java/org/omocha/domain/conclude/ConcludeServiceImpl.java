package org.omocha.domain.conclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
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
	private final ChatService chatService;

	@Override
	@Transactional
	public List<Long> concludeAuction() {
		List<Auction> expiredBiddingAuctionList = auctionReader.getExpiredBiddingAuctionList();
		List<Long> concludedAuctionIdList = new ArrayList<>();

		for (Auction auction : expiredBiddingAuctionList) {
			Optional<Bid> optionalHighestBid = bidReader.findHighestBid(auction.getAuctionId());

			optionalHighestBid.ifPresentOrElse(highestBid -> {
				concludeStore.store(auction, highestBid);

				Member highestBuyer = highestBid.getBuyer();

				auction.statusConcluded();
				concludedAuctionIdList.add(auction.getAuctionId());

				var chatRoomCommand = new ChatCommand.AddChatRoom(
					auction.getAuctionId(), highestBuyer.getMemberId());
				chatService.addChatRoom(chatRoomCommand);
			}, () -> {
				auction.statusNoBids();
				concludedAuctionIdList.add(auction.getAuctionId());
			});
		}

		return concludedAuctionIdList;
	}
}