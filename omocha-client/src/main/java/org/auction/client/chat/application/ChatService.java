package org.auction.client.chat.application;

import static org.auction.client.common.code.ChatCode.*;

import java.time.LocalDateTime;

import org.auction.client.chat.interfaces.response.ChatMessageResponse;
import org.auction.client.chat.interfaces.response.ChatRoomDetailsResponse;
import org.auction.client.exception.chat.ChatRoomAccessException;
import org.auction.client.exception.chat.ChatRoomNotFoundException;
import org.auction.domain.chat.ChatEntity;
import org.auction.domain.chat.ChatRoomEntity;
import org.auction.domain.chat.MessageType;
import org.auction.infra.ChatRepository;
import org.auction.infra.ChatRoomRepository;
import org.auction.domain.member.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final ChatRoomRepository roomRepository;

	public ChatEntity createChat(
		Long roomId,
		MemberEntity sender,
		String message,
		MessageType type
	) {

		// 채팅방 조회
		ChatRoomEntity room = roomRepository.findById(roomId)
			.orElseThrow(() -> new ChatRoomNotFoundException(CHATROOM_NOT_FOUND));

		// 채팅 메시지 생성 및 저장
		ChatEntity chat = ChatEntity.builder()
			.chatRoom(room)
			.sender(sender)
			.message(message)
			.type(type)
			.build();
		return chatRepository.save(chat);
	}

	@Transactional(readOnly = true)
	public ChatRoomDetailsResponse findChatRoomMessages(
		Long roomId,
		MemberEntity memberEntity,
		LocalDateTime cursor,
		Pageable pageable
	) {
		// 채팅방 조회
		ChatRoomEntity roomEntity = roomRepository.findById(roomId)
			.orElseThrow(() -> new ChatRoomNotFoundException(CHATROOM_NOT_FOUND));

		// 현재 사용자가 채팅방에 참여하는지 확인
		if (!roomEntity.validateParticipant(memberEntity)) {
			throw new ChatRoomAccessException(CHATROOM_ACCESS_UNAUTHORIZED);
		}

		Slice<ChatMessageResponse> messages = chatRepository.findChatMessagesByRoomId(
			roomId, cursor, pageable
		).map(ChatMessageResponse::toDto);

		return ChatRoomDetailsResponse.toDto(roomEntity, messages);
	}
}