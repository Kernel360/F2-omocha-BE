package org.omocha.api.chat.dto;

import org.omocha.domain.chat.Chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatDto {

	public record ChatMessageRequest(
		@NotBlank Chat.MessageType messageType,
		@NotNull Long senderMemberId,
		@NotBlank String message
	) {

	}

}
