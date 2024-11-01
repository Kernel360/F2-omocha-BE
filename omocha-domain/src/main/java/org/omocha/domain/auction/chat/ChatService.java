package org.omocha.domain.auction.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatService {

	void addChatRoom(ChatCommand.CreateChatRoom addChatRoom);

	Slice<ChatInfo.MyChatRoomInfo> findMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retriveMyChatRoom,
		Pageable pageable
	);

	Chat saveChatMessage(ChatCommand.SaveChatMessage chatMessage);

	void sendChatMessage(Chat chatMessage);

	Slice<ChatInfo.ChatMessage> findChatRoomMessages(
		ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable
	);
}
