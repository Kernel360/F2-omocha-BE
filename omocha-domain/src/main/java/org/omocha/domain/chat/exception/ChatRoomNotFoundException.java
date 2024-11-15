package org.omocha.domain.chat.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ChatRoomNotFoundException extends ChatException {
	public ChatRoomNotFoundException(Long roomId) {
		super(
			ErrorCode.CHATROOM_NOT_FOUND,
			"채팅방이 존재하지 않습니다. roomId: " + roomId
		);
	}
}
