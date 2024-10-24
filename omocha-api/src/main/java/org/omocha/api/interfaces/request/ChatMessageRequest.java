package org.omocha.api.interfaces.request;

import org.omocha.domain.auction.chat.MessageType;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(
	@NotBlank
	MessageType messageType,
	Long senderId,
	@NotBlank
	String message
) {
}
