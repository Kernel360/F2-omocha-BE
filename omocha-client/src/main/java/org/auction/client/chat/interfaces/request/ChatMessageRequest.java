package org.auction.client.chat.interfaces.request;

import org.auction.domain.chat.domain.enums.MessageType;

public record ChatMessageRequest(
	MessageType messageType,
	String message
) {
}
