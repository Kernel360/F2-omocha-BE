package org.auction.client.chat.application;

import static org.auction.client.common.code.AuctionCode.*;
import static org.auction.client.common.code.BidCode.*;
import static org.auction.client.common.code.ChatCode.*;

import org.auction.client.bid.application.BidService;
import org.auction.client.chat.interfaces.response.ChatRoomInfoResponse;
import org.auction.client.exception.auction.AuctionNotFoundException;
import org.auction.client.exception.bid.NoBidsException;
import org.auction.client.exception.chat.ChatRoomAlreadyExistsException;
import org.auction.client.exception.chat.SellerIsBuyerException;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.infrastructure.AuctionRepository;
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

	@Transactional
	public ChatRoomInfoResponse addChatRoom(
		MemberEntity buyer,
		Long auctionId
	) {
		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));

		MemberEntity seller = auctionEntity.getMemberEntity();

		if (seller.getMemberId() == buyer.getMemberId()) {
			throw new SellerIsBuyerException(SELLER_IS_BUYER);
		}

		// TODO : 낙찰에서 가격을 조회하는 걸로 변경해야함, 가격이 없으면 예외를 터트려 줘야함
		Long nowPrice = bidService.getCurrentHighestBidPrice(auctionEntity);

		if (nowPrice == null) {
			throw new NoBidsException(NO_BIDS_FOUND);
		}

		if (roomRepository.existsByAuctionId(auctionId)) {
			throw new ChatRoomAlreadyExistsException(CHATROOM_ALREADY_EXISTS);
		}

		ChatRoomEntity chatRoom = ChatRoomEntity.builder()
			.roomName(auctionEntity.getTitle())
			.nowPrice(nowPrice)
			.buyer(buyer)
			.seller(seller)
			.auctionId(auctionId)
			.build();
		roomRepository.save(chatRoom);

		return ChatRoomInfoResponse.toDto(chatRoom);
	}

	@Transactional(readOnly = true)
	public Slice<ChatRoomInfoResponse> findMyChatRooms(
		MemberEntity memberEntity,
		Pageable pageable
	) {
		return roomRepository.findChatRoomsByUser(memberEntity, pageable)
			.map(ChatRoomInfoResponse::toDto);
	}

}