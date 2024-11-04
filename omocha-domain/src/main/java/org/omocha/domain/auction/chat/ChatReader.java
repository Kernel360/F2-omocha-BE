package org.omocha.domain.auction.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatReader {
	boolean existsByAuctionId(Long chatRoomId);

	Slice<ChatInfo.RetrieveMyChatRoom> getMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveCommand,
		Pageable pageable
	);

	Slice<ChatInfo.RetrieveChatRoomMessage> getChatRoomMessages(
		ChatCommand.RetrieveChatRoomMessage chatMessageCommand,
		Pageable pageable
	);

	ChatRoom getChatRoom(Long roomId);
}
