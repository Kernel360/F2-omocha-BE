package org.omocha.infra;

import org.omocha.domain.auction.chat.ChatCommand;
import org.omocha.domain.auction.chat.ChatInfo;
import org.omocha.domain.auction.chat.ChatReader;
import org.omocha.domain.auction.chat.ChatRoom;
import org.omocha.domain.exception.ChatRoomNotFoundException;
import org.omocha.infra.repository.ChatRepository;
import org.omocha.infra.repository.ChatRoomRepository;
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
	public Slice<ChatInfo.MyChatRoomInfo> findMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveMyChatRoom,
		Pageable pageable
	) {
		return chatRoomRepository.findMyChatRooms(retrieveMyChatRoom, pageable);
	}

	@Override
	public Slice<ChatInfo.ChatMessage> findChatMessages(ChatCommand.RetrieveChatRoomMessage chatRoomMessage,
		Pageable pageable) {
		return chatRepository.findChatMessagesByRoomId(
			chatRoomMessage,
			pageable
		);
	}

	@Override
	public ChatRoom findChatRoom(Long roomId) {
		return chatRoomRepository.findById(roomId)
			.orElseThrow(() -> new ChatRoomNotFoundException(roomId));
	}
}
