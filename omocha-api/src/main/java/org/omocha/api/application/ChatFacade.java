package org.omocha.api.application;

import org.omocha.domain.auction.chat.Chat;
import org.omocha.domain.auction.chat.ChatCommand;
import org.omocha.domain.auction.chat.ChatInfo;
import org.omocha.domain.auction.chat.ChatService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatFacade {

	private final ChatService chatService;

	public void addChatRoom(ChatCommand.CreateChatRoom addChatRoom) {
		chatService.addChatRoom(addChatRoom);
	}

	public Slice<ChatInfo.MyChatRoomInfo> findMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveChatRoom,
		Pageable pageable
	) {
		return chatService.findMyChatRooms(retrieveChatRoom, pageable);
	}

	public void processChatMessage(ChatCommand.SaveChatMessage chatMessage) {
		Chat savedChat = chatService.saveChatMessage(chatMessage);
		chatService.sendChatMessage(savedChat);
	}

	public Slice<ChatInfo.ChatMessage> findChatRoomMessages(
		ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable
	) {
		return chatService.findChatRoomMessages(chatRoomMessage, pageable);
	}

}
