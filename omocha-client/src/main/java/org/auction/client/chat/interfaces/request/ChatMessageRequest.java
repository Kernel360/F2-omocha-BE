package org.auction.client.chat.interfaces.request;

import org.auction.domain.chat.domain.enums.MessageType;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(
	@NotBlank
	MessageType messageType,
	@NotBlank
	String message
) {
}
