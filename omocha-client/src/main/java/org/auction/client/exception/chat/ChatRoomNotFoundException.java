package org.auction.client.exception.chat;

import org.auction.client.common.code.ChatCode;

public class ChatRoomNotFoundException extends ChatException {
	public ChatRoomNotFoundException(
		ChatCode chatCode
	) {
		super(chatCode);
	}

	public ChatRoomNotFoundException(
		ChatCode chatCode,
		String detailMessage
	) {
		super(chatCode, detailMessage);
	}
}
