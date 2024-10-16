package org.auction.client.chat.interfaces.response;

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

		return new ChatRoomDetailsResponse(
			// TODO : 추후 refactoring 필요
			ChatRoomInfoDto.toDto(chatRoomEntity, null),
			new SliceResponse<>(messages)
		);
	}
}