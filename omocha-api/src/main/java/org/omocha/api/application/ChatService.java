package org.omocha.api.application;

import static org.omocha.client.common.code.ChatCode.*;

import java.time.LocalDateTime;

import org.omocha.client.chat.interfaces.response.ChatMessageResponse;
import org.omocha.client.chat.interfaces.response.ChatRoomDetailsResponse;
import org.omocha.client.exception.chat.ChatRoomAccessException;
import org.omocha.client.exception.chat.ChatRoomNotFoundException;
import org.omocha.domain.auction.chat.ChatEntity;
import org.omocha.domain.auction.chat.ChatRoomEntity;
import org.omocha.domain.auction.chat.MessageType;
import org.omocha.infra.ChatRepository;
import org.omocha.infra.ChatRoomRepository;
import org.omocha.domain.member.MemberEntity;
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
			.orElseThrow(() -> new ChatRoomNotFoundException(ChatCode.CHATROOM_NOT_FOUND));

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
			.orElseThrow(() -> new ChatRoomNotFoundException(ChatCode.CHATROOM_NOT_FOUND));

		// 현재 사용자가 채팅방에 참여하는지 확인
		if (!roomEntity.validateParticipant(memberEntity)) {
			throw new ChatRoomAccessException(ChatCode.CHATROOM_ACCESS_UNAUTHORIZED);
		}

		Slice<ChatMessageResponse> messages = chatRepository.findChatMessagesByRoomId(
			roomId, cursor, pageable
		).map(ChatMessageResponse::toDto);

		return ChatRoomDetailsResponse.toDto(roomEntity, messages);
	}
}