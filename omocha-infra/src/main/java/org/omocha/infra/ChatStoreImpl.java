package org.omocha.infra;

import org.omocha.domain.auction.chat.Chat;
import org.omocha.domain.auction.chat.ChatRoom;
import org.omocha.domain.auction.chat.ChatStore;
import org.omocha.infra.repository.ChatRepository;
import org.omocha.infra.repository.ChatRoomRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatStoreImpl implements ChatStore {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatRepository chatRepository;

	@Override
	public ChatRoom store(ChatRoom chatRoom) {
		return chatRoomRepository.save(chatRoom);
	}

	@Override
	public Chat store(Chat chat) {
		return chatRepository.save(chat);
	}
}
