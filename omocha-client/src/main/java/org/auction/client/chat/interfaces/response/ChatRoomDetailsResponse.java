package org.auction.client.chat.interfaces.response;

import java.time.LocalDateTime;

import org.auction.client.common.dto.SliceResponse;
import org.auction.domain.chat.domain.dto.ChatRoomInfoDto;
import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.springframework.data.domain.Slice;

public record ChatRoomDetailsResponse(
	ChatRoomInfoDto chatRoomInfo, // 채팅방 정보
	SliceResponse<ChatMessageResponse> messages // 메시지 목록
) {
	public static ChatRoomDetailsResponse toDto(
		ChatRoomEntity chatRoomEntity,
		Slice<ChatMessageResponse> messages
	) {
		LocalDateTime lastMessageTime;

		if (messages != null && !messages.isEmpty()) {
			// 메시지가 있을 경우, 가장 최신 메시지의 시간을 가져옴
			lastMessageTime = messages.getContent().get(0).createdDate();
		} else {
			// 메시지가 없을 경우, 채팅방 생성 시간을 사용
			lastMessageTime = chatRoomEntity.getCreatedAt();
		}

		return new ChatRoomDetailsResponse(
			ChatRoomInfoDto.toDto(chatRoomEntity, lastMessageTime),
			new SliceResponse<>(messages)
		);
	}
}