package org.omocha.domain.auction.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatService {

	void addChatRoom(ChatCommand.AddChatRoom addChatRoom);

	Slice<ChatInfo.RetrieveMyChatRoom> retrieveMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveCommand,
		Pageable pageable
	);

	Chat saveChatMessage(ChatCommand.AddChatMessage chatMessage);

	void sendChatMessage(Chat chatMessage);

	Slice<ChatInfo.RetrieveChatRoomMessage> retrieveChatRoomMessages(
		ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable
	);
}
