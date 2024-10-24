package org.auction.infra.custom;

import java.time.LocalDateTime;

import org.auction.domain.chat.ChatEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRepositoryCustom {

	Slice<ChatEntity> findChatMessagesByRoomId(
		Long roomId,
		LocalDateTime cursor,
		Pageable pageable
	);
}
