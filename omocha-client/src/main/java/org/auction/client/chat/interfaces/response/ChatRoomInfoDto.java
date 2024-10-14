package org.auction.client.chat.interfaces.response;

import java.time.LocalDateTime;

import org.auction.domain.chat.domain.entity.ChatRoomEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ChatRoomInfoDto(
	Long auctionId, // 경매 ID
	Long roomId, // 채팅방 ID
	String roomName, // 경매 TITLE
	Long sellerId, // 판매자 ID
	String sellerName, // 판매자 NAME
	Long concludePrice,
	Long buyerId, // 구매자 ID
	String buyerName,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdDate
) {

	public static ChatRoomInfoDto toDto(ChatRoomEntity chatRoomEntity) {
		return new ChatRoomInfoDto(
			chatRoomEntity.getAuctionId(),
			chatRoomEntity.getChatRoomId(),
			chatRoomEntity.getRoomName(),
			chatRoomEntity.getSeller().getMemberId(),
			chatRoomEntity.getSeller().getNickname(),
			chatRoomEntity.getConcludePrice(),
			chatRoomEntity.getBuyer().getMemberId(),
			chatRoomEntity.getBuyer().getNickname(),
			chatRoomEntity.getCreatedAt()
		);
	}

}
