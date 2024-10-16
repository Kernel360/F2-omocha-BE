package org.auction.client.bid.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.auction.client.auction.application.AuctionService;
import org.auction.client.chat.application.ChatRoomService;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.infrastructure.AuctionRepository;
import org.auction.domain.bid.entity.BidEntity;
import org.auction.domain.bid.entity.ConcludeEntity;
import org.auction.domain.bid.infrastructure.ConcludeRepository;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConcludeService {

	private final AuctionRepository auctionRepository;
	private final ConcludeRepository concludeRepository;
	private final BidService bidService;
	private final AuctionService auctionService;
	private final ChatRoomService chatRoomService;

	@Transactional(readOnly = true)
	public List<AuctionEntity> findBiddingAuctions() {
		return auctionRepository.findByAuctionStatusAndEndDateBefore(AuctionStatus.BIDDING, LocalDateTime.now());
	}

	@Transactional
	public void concludeAuctionIfEnded(
		AuctionEntity auction
	) {
		if (auction.getEndDate().isBefore(LocalDateTime.now())) {
			// TODO : DB에서 바로 꺼내오도록 변경
			Optional<BidEntity> optionalHighestBid = bidService.getCurrentHighestBid(auction);

			optionalHighestBid.ifPresentOrElse(highestBid -> {
				auctionService.modifyAuctionStatus(auction, AuctionStatus.CONCLUDED);

				createAuctionConclude(auction, highestBid);

				MemberEntity highestBuyer = highestBid.getBuyerEntity();
				chatRoomService.addChatRoom(highestBuyer, auction.getAuctionId(), highestBid.getBidPrice());
			}, () -> {
				auctionService.modifyAuctionStatus(auction, AuctionStatus.NO_BIDS);
			});
		}
	}

	private void createAuctionConclude(
		AuctionEntity auction,
		BidEntity highestBid
	) {
		ConcludeEntity concludeEntity = ConcludeEntity.builder()
			.concludePrice(highestBid.getBidPrice())
			.concludedAt(highestBid.getCreatedAt())
			.auctionEntity(auction)
			.buyerEntity(highestBid.getBuyerEntity())
			.build();

		concludeRepository.save(concludeEntity);
	}
}
