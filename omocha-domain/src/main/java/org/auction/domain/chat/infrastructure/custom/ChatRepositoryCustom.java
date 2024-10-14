package org.auction.domain.chat.infrastructure.custom;

import org.auction.domain.chat.domain.entity.ChatEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRepositoryCustom {

	Slice<ChatEntity> findChatMessagesByRoomId(
		Long roomId,
		Pageable pageable
	);
}
