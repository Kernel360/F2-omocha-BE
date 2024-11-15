package org.omocha.infra.chat.repository;

import org.omocha.domain.chat.ChatCommand;
import org.omocha.domain.chat.ChatInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRepositoryCustom {
	Slice<ChatInfo.RetrieveChatRoomMessage> getChatRoomMessagesByRoomId(
		ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable
	);
}
