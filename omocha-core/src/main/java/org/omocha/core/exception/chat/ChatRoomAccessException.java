package org.omocha.core.exception.chat;

import org.omocha.client.common.code.ChatCode;

public class ChatRoomAccessException extends ChatException {

	public ChatRoomAccessException(
		ChatCode chatCode
	) {
		super(chatCode);
	}

	public ChatRoomAccessException(
		ChatCode chatCode,
		String detailMessage
	) {
		super(chatCode, detailMessage);
	}
}
