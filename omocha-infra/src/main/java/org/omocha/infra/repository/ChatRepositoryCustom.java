package org.omocha.infra.repository;

import org.omocha.domain.auction.chat.ChatCommand;
import org.omocha.domain.auction.chat.ChatInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRepositoryCustom {
	Slice<ChatInfo.RetrieveChatRoomMessage> getChatRoomMessagesByRoomId(
		ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable
	);
}
