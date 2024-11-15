package org.omocha.api.chat.dto;

import org.omocha.domain.chat.Chat;

import jakarta.validation.constraints.NotBlank;

public class ChatDto {

	public record ChatMessageRequest(
		@NotBlank(message = "MessageType이 공백 또는 null 이면 안됨")
		Chat.MessageType messageType,
		Long senderId,
		@NotBlank(message = "Message에 내용이 없음")
		String message

	) {

	}

}
