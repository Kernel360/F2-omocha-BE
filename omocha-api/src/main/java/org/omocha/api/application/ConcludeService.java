package org.omocha.api.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.omocha.client.chat.application.ChatRoomService;
import org.omocha.domain.auction.AuctionEntity;
import org.omocha.domain.auction.AuctionStatus;
import org.omocha.infra.AuctionRepository;
import org.omocha.domain.auction.bid.BidEntity;
import org.omocha.domain.auction.bid.ConcludeEntity;
import org.omocha.infra.ConcludeRepository;
import org.omocha.domain.member.MemberEntity;
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
