package org.omocha.core.exception.chat;

import org.omocha.client.common.code.ChatCode;

public class ChatRoomAlreadyExistsException extends ChatException {
	public ChatRoomAlreadyExistsException(
		ChatCode chatCode
	) {
		super(chatCode);
	}

	public ChatRoomAlreadyExistsException(
		ChatCode chatCode,
		String detailMessage
	) {
		super(chatCode, detailMessage);
	}
}
