package org.auction.client.chat.interfaces.response;

import java.time.LocalDateTime;

import org.auction.domain.chat.domain.entity.ChatEntity;
import org.auction.domain.chat.domain.enums.MessageType;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ChatMessageResponse(
	MessageType type,
	Long roomId,
	String senderNickName,
	Long senderId,
	String message,
	// TODO : profile image 추가 해야함
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdDate
) {
	public static ChatMessageResponse toDto(ChatEntity chatEntity) {
		return new ChatMessageResponse(
			chatEntity.getType(),
			chatEntity.getChatRoom().getChatRoomId(),
			chatEntity.getSender().getNickname(),
			chatEntity.getSender().getMemberId(),
			chatEntity.getMessage(),
			chatEntity.getCreatedAt()
		);
	}
}
