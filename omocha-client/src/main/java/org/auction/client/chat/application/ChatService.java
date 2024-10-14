package org.auction.client.chat.application;

import static org.auction.client.common.code.ChatCode.*;

import org.auction.client.chat.interfaces.response.ChatMessageResponse;
import org.auction.client.chat.interfaces.response.ChatRoomDetailsResponse;
import org.auction.client.exception.chat.ChatRoomAccessException;
import org.auction.client.exception.chat.ChatRoomNotFoundException;
import org.auction.domain.chat.domain.entity.ChatEntity;
import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.auction.domain.chat.domain.enums.MessageType;
import org.auction.domain.chat.infrastructure.ChatRepository;
import org.auction.domain.chat.infrastructure.ChatRoomRepository;
import org.auction.domain.member.domain.entity.MemberEntity;
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

	public ChatEntity createChat(Long roomId, MemberEntity sender, String message, MessageType type) {
		
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
		Pageable pageable
	) {
		// 채팅방 조회
		ChatRoomEntity roomEntity = roomRepository.findById(roomId)
			.orElseThrow(() -> new ChatRoomNotFoundException(CHATROOM_NOT_FOUND));

		// 현재 사용자가 채팅방에 참여하는지 확인
		if (!roomEntity.validateParticipant(memberEntity)) {
			throw new ChatRoomAccessException(CHATROOM_ACCESS_UNAUTHORIZED);
		}

		Slice<ChatMessageResponse> messages = chatRepository.findChatMessagesByRoomId(roomId, pageable)
			.map(ChatMessageResponse::toDto);

		return ChatRoomDetailsResponse.toDto(roomEntity, messages);
	}
}