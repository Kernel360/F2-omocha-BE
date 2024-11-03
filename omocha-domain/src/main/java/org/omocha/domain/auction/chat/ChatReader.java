package org.omocha.domain.auction.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatReader {
	boolean existsByAuctionId(Long chatRoomId);

	Slice<ChatInfo.MyChatRoomInfo> findMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveMyChatRoom,
		Pageable pageable
	);

	Slice<ChatInfo.ChatMessage> findChatMessages(
		ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable
	);

	ChatRoom findChatRoom(Long roomId);
}
