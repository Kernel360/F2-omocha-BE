package org.omocha.infra.chat.repository;

import org.omocha.domain.chat.ChatCommand;
import org.omocha.domain.chat.ChatInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRoomRepositoryCustom {
	Slice<ChatInfo.RetrieveMyChatRoom> getMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveMyChatRoom,
		Pageable pageable
	);
}
