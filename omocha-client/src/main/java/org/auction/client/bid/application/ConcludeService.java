package org.auction.client.bid.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
			Optional<BidEntity> optionalHighestBid = bidService.getCurrentHighestBid(auction.getAuctionId());

			optionalHighestBid.ifPresentOrElse(highestBid -> {
				modifyAuctionStatus(auction, AuctionStatus.CONCLUDED);

				createAuctionConclude(auction, highestBid);

				MemberEntity highestBuyer = highestBid.getBuyerEntity();
				chatRoomService.addChatRoom(highestBuyer, auction.getAuctionId(), highestBid.getBidPrice());
			}, () -> {
				modifyAuctionStatus(auction, AuctionStatus.NO_BIDS);
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

	@Transactional(readOnly = true)
	public Long findConcludePrice(Long auctionId) {
		return concludeRepository.findByAuctionEntityAuctionId(auctionId)
			.map(ConcludeEntity::getConcludePrice)
			.orElse(null);
	}

	// TODO: 순환참조 문제 해결을 위해 여기로 이동, 추후 리팩토링 필요
	private void modifyAuctionStatus(AuctionEntity auction, AuctionStatus auctionStatus) {
		auction.modifyStatus(auctionStatus);
		auctionRepository.save(auction);
	}
}
