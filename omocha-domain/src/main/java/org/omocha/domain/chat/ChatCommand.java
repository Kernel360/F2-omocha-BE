package org.omocha.domain.chat;

import java.time.LocalDateTime;

import org.omocha.domain.auction.Auction;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ChatCommand {

	public record AddChatRoom(
		Long auctionId,
		Long buyerMemberId
	) {
		public ChatRoom toEntity(Auction auction) {
			return ChatRoom.builder()
				.roomName(auction.getTitle())
				.buyerMemberId(buyerMemberId)
				.sellerMemberId(auction.getMemberId())
				.auctionId(auction.getAuctionId())
				.build();
		}
	}

	public record RetrieveMyChatRoom(
		Long memberId
	) {
	}

	public record AddChatMessage(
		Chat.MessageType messageType,
		Long senderMemberId,
		String message,
		Long roomId
	) {
		public Chat toEntity() {
			return Chat.builder()
				.messageType(messageType)
				.senderMemberId(senderMemberId)
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
