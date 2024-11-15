package org.omocha.domain.chat.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ChatRoomAccessException extends ChatException {
	public ChatRoomAccessException(Long memberId) {
		super(
			ErrorCode.CHATROOM_ACCESS_FAIL,
			"채팅방에 참여하지 않는 회원입니다. memberId: " + memberId
		);
	}
}
