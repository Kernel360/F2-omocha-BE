package org.auction.client.chat.application;

import static org.auction.client.common.code.AuctionCode.*;
import static org.auction.client.common.code.BidCode.*;
import static org.auction.client.common.code.ChatCode.*;

import org.auction.client.bid.application.BidService;
import org.auction.client.exception.auction.AuctionNotFoundException;
import org.auction.client.exception.bid.NoBidsException;
import org.auction.client.exception.chat.ChatRoomAlreadyExistsException;
import org.auction.client.exception.chat.SellerIsBuyerException;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.infrastructure.AuctionRepository;
import org.auction.domain.chat.domain.dto.ChatRoomInfoDto;
import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.auction.domain.chat.infrastructure.ChatRoomRepository;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository roomRepository;
	private final AuctionRepository auctionRepository;
	private final BidService bidService;

	// TODO: 채팅 테스트 완료되면 로직 변경 필요
	@Transactional
	public void addChatRoom(
		MemberEntity buyer,
		Long auctionId
	) {
		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));

		MemberEntity seller = auctionEntity.getMemberEntity();

		// TODO: 추후 삭제 예정
		if (seller.getMemberId() == buyer.getMemberId()) {
			throw new SellerIsBuyerException(SELLER_IS_BUYER);
		}

		// TODO: 추후 삭제 예정
		Long concludePrice = bidService.getCurrentHighestBidPrice(auctionEntity);

		// TODO: 추후 삭제 예정
		if (concludePrice == null) {
			throw new NoBidsException(NO_BIDS_FOUND);
		}

		if (roomRepository.existsByAuctionId(auctionId)) {
			throw new ChatRoomAlreadyExistsException(CHATROOM_ALREADY_EXISTS);
		}

		ChatRoomEntity chatRoom = ChatRoomEntity.builder()
			.roomName(auctionEntity.getTitle())
			.concludePrice(concludePrice)
			.buyer(buyer)
			.seller(seller)
			.auctionId(auctionId)
			.build();
		roomRepository.save(chatRoom);
	}

	@Transactional(readOnly = true)
	public Slice<ChatRoomInfoDto> findMyChatRooms(
		MemberEntity memberEntity,
		Pageable pageable
	) {
		return roomRepository.findChatRoomsByUser(memberEntity, pageable);
	}

}