package org.omocha.api.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatDto {

	public record ChatMessageRequest(
		@NotBlank String messageType,
		@NotNull Long senderMemberId,
		@NotBlank String message
	) {

	}

}
