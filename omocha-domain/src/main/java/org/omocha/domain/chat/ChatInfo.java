package org.omocha.domain.chat;

import static org.omocha.domain.chat.Chat.*;

import java.time.LocalDateTime;

import org.omocha.domain.member.Member;

import com.querydsl.core.annotations.QueryProjection;

public class ChatInfo {

	public record RetrieveMyChatRoom(
		Long auctionId,
		Long roomId,
		String roomName,
		Long sellerMemberId,
		String sellerName,
		String sellerProfileImage,
		String thumbnailPath,
		Long concludePrice,
		Long buyerMemberId,
		String buyerName,
		String buyerProfileImage,
		LocalDateTime createdDate,
		LocalDateTime lastMessageTime,
		String lastMessage
	) {
		@QueryProjection
		public RetrieveMyChatRoom(
			Long auctionId,
			Long roomId,
			String roomName,
			Long sellerMemberId,
			String sellerName,
			String sellerProfileImage,
			String thumbnailPath,
			Long concludePrice,
			Long buyerMemberId,
			String buyerName,
			String buyerProfileImage,
			LocalDateTime createdDate,
			LocalDateTime lastMessageTime,
			String lastMessage
		) {
			this.auctionId = auctionId;
			this.roomId = roomId;
			this.roomName = roomName;
			this.sellerMemberId = sellerMemberId;
			this.sellerName = sellerName;
			this.sellerProfileImage = sellerProfileImage;
			this.thumbnailPath = thumbnailPath;
			this.concludePrice = concludePrice;
			this.buyerMemberId = buyerMemberId;
			this.buyerName = buyerName;
			this.buyerProfileImage = buyerProfileImage;
			this.createdDate = createdDate;
			this.lastMessageTime = lastMessageTime;
			this.lastMessage = lastMessage;
		}

	}

	public record RetrieveChatRoomMessage(
		MessageType messageType,
		Long senderMemberId,
		Long roomId,
		String nickname,
		String senderProfileImage,
		String message,
		LocalDateTime createdAt
	) {
		@QueryProjection
		public RetrieveChatRoomMessage(
			MessageType messageType,
			Long senderMemberId,
			Long roomId,
			String nickname,
			String senderProfileImage,
			String message,
			LocalDateTime createdAt
		) {
			this.messageType = messageType;
			this.senderMemberId = senderMemberId;
			this.roomId = roomId;
			this.nickname = nickname;
			this.senderProfileImage = senderProfileImage;
			this.message = message;
			this.createdAt = createdAt;
		}

		public static RetrieveChatRoomMessage toInfo(
			Member sender,
			Chat savedChat
		) {
			return new RetrieveChatRoomMessage(
				savedChat.getMessageType(),
				sender.getMemberId(),
				savedChat.getRoomId(),
				sender.getNickname(),
				sender.getProfileImageUrl(),
				savedChat.getMessage(),
				savedChat.getCreatedAt()
			);
		}

	}

}
