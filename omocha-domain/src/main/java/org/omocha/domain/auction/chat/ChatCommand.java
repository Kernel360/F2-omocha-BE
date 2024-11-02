package org.omocha.domain.auction.chat;

import java.time.LocalDateTime;

import org.omocha.domain.auction.Auction;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ChatCommand {

	public record CreateChatRoom(
		Long auctionId,
		Long buyerId
	) {
		public ChatRoom toEntity(Auction auction) {
			return ChatRoom.builder()
				.roomName(auction.getTitle())
				.buyerId(buyerId)
				.sellerId(auction.getMemberId())
				.auctionId(auction.getAuctionId())
				.build();
		}
	}

	public record RetrieveMyChatRoom(
		Long memberId
	) {
	}

	public record SaveChatMessage(
		Chat.MessageType messageType,
		Long senderId,
		String message,
		Long roomId
	) {
		public Chat toEntity() {
			return Chat.builder()
				.messageType(messageType)
				.senderId(senderId)
				.message(message)
				.roomId(roomId)
				.build();
		}
	}

	public record RetrieveChatRoomMessage(
		Long roomId,
		Long memberId,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime cursor
	) {

	}

}
