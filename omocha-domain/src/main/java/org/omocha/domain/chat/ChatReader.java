package org.omocha.domain.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatReader {
	boolean existsByAuctionId(Long chatRoomId);

	Slice<ChatInfo.RetrieveMyChatRoom> getMyChatRoomList(
		ChatCommand.RetrieveMyChatRoom retrieveCommand,
		Pageable pageable
	);

	Slice<ChatInfo.RetrieveChatRoomMessage> getChatRoomMessageList(
		ChatCommand.RetrieveChatRoomMessage chatMessageCommand,
		Pageable pageable
	);

	ChatRoom getChatRoom(Long roomId);
}
