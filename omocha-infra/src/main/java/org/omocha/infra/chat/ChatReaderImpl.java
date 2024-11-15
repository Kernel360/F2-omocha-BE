package org.omocha.infra.chat;

import org.omocha.domain.chat.ChatCommand;
import org.omocha.domain.chat.ChatInfo;
import org.omocha.domain.chat.ChatReader;
import org.omocha.domain.chat.ChatRoom;
import org.omocha.domain.chat.exception.ChatRoomNotFoundException;
import org.omocha.infra.chat.repository.ChatRepository;
import org.omocha.infra.chat.repository.ChatRoomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatReaderImpl implements ChatReader {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatRepository chatRepository;

	@Override
	public boolean existsByAuctionId(Long chatRoomId) {
		return chatRoomRepository.existsByAuctionId(chatRoomId);
	}

	@Override
	public Slice<ChatInfo.RetrieveMyChatRoom> getMyChatRoomList(
		ChatCommand.RetrieveMyChatRoom retrieveCommand,
		Pageable pageable
	) {
		return chatRoomRepository.getMyChatRooms(retrieveCommand, pageable);
	}

	@Override
	public Slice<ChatInfo.RetrieveChatRoomMessage> getChatRoomMessageList(
		ChatCommand.RetrieveChatRoomMessage chatMessageCommand,
		Pageable pageable
	) {
		return chatRepository.getChatRoomMessagesByRoomId(
			chatMessageCommand,
			pageable
		);
	}

	@Override
	public ChatRoom getChatRoom(Long roomId) {
		return chatRoomRepository.findById(roomId)
			.orElseThrow(() -> new ChatRoomNotFoundException(roomId));
	}
}
