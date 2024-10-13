package org.auction.client.bid.application;

import java.time.LocalDateTime;
import java.util.List;

import org.auction.client.auction.application.AuctionService;
import org.auction.client.chat.application.ChatRoomService;
import org.auction.client.member.application.MemberService;
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
	private final MemberService memberService;
	private final ChatRoomService chatRoomService;

	@Transactional(readOnly = true)
	public List<AuctionEntity> findBiddingAuctions() {
		return auctionRepository.findByAuctionStatusAndEndDateBefore(AuctionStatus.BIDDING, LocalDateTime.now());
	}

	@Transactional
	public void concludeAuctionIfEnded(AuctionEntity auction) {
		if (auction.getEndDate().isBefore(LocalDateTime.now())) {
			// 옥션 상태 변경 (입찰중 => 낙찰완료)
			auctionService.modifyAuctionStatus(auction, AuctionStatus.CONCLUDED);

			// 낙찰 row insert
			createAuctionConclude(auction);

			// 채팅방 생성
			// TODO: 채팅방은 생성만 해주고 response가 없어야 함 => scheduler는 return 값이 없음
			//       채팅에 대해서는 추후 수정 필요
			MemberEntity highestBuyer = bidService.getCurrentHighestBid(auction).getMemberEntity();
			chatRoomService.addChatRoom(highestBuyer, auction.getAuctionId());
		}
	}

	private void createAuctionConclude(AuctionEntity auction) {
		BidEntity highestBid = bidService.getCurrentHighestBid(auction);

		ConcludeEntity concludeEntity = ConcludeEntity.builder()
			.concludePrice(highestBid.getBidPrice())
			.concludedAt(highestBid.getCreatedAt())
			.auctionEntity(auction)
			.memberEntity(highestBid.getMemberEntity())
			.build();

		concludeRepository.save(concludeEntity);
	}
}
