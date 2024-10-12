package org.auction.client.chat.application;

import static org.auction.client.common.code.AuctionCode.*;
import static org.auction.client.common.code.BidCode.*;
import static org.auction.client.common.code.ChatCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.auction.client.bid.application.BidService;
import org.auction.client.chat.interfaces.response.ChatRoomListResponse;
import org.auction.client.chat.interfaces.response.CreateChatRoomResponse;
import org.auction.client.exception.auction.AuctionNotFoundException;
import org.auction.client.exception.bid.NoBidsException;
import org.auction.client.exception.chat.ChatRoomAlreadyExistsException;
import org.auction.client.exception.chat.SellerIsBuyerException;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.infrastructure.AuctionRepository;
import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.auction.domain.chat.infrastructure.ChatRoomRepository;
import org.auction.domain.member.domain.entity.MemberEntity;
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
	public CreateChatRoomResponse addChatRoom(
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

		return CreateChatRoomResponse.toDto(chatRoom);
	}

	// TODO : pagination 으로 추후에 변경할거임
	@Transactional(readOnly = true)
	public ChatRoomListResponse findMyChatRooms(MemberEntity memberEntity) {
		List<ChatRoomEntity> chatRooms = roomRepository.findByBuyerOrSeller(memberEntity, memberEntity);

		List<CreateChatRoomResponse> chatRoomResponses = chatRooms.stream()
			.map(CreateChatRoomResponse::toDto)
			.collect(Collectors.toList());

		return new ChatRoomListResponse(chatRoomResponses);
	}

	// TODO : 채팅방 상세 내용 불러오는 로직 필요

}