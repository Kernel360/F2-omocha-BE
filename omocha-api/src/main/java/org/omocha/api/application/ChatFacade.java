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

	public void addChatRoom(ChatCommand.AddChatRoom addCommand) {
		chatService.addChatRoom(addCommand);
	}

	public Slice<ChatInfo.RetrieveMyChatRoom> retrieveMyChatRoom(
		ChatCommand.RetrieveMyChatRoom retrieveCommand,
		Pageable pageable
	) {
		return chatService.retrieveMyChatRooms(retrieveCommand, pageable);
	}

	public void processChatMessage(ChatCommand.AddChatMessage chatMessage) {
		Chat savedChat = chatService.saveChatMessage(chatMessage);
		chatService.sendChatMessage(savedChat);
	}

	public Slice<ChatInfo.RetrieveChatRoomMessage> retrieveChatRoomMessage(
		ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable
	) {
		return chatService.retrieveChatRoomMessages(chatRoomMessage, pageable);
	}

}
