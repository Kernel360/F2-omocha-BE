package org.auction.client.chat.interfaces.response;

import org.auction.client.common.dto.SliceResponse;
import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.springframework.data.domain.Slice;

public record ChatRoomDetailsResponse(
	ChatRoomInfoResponse chatRoomInfo, // 채팅방 정보
	SliceResponse<ChatMessageResponse> messages // 메시지 목록
) {
	public static ChatRoomDetailsResponse toDto(
		ChatRoomEntity chatRoomEntity,
		Slice<ChatMessageResponse> messages
	) {
		return new ChatRoomDetailsResponse(
			ChatRoomInfoResponse.toDto(chatRoomEntity),
			new SliceResponse<>(messages)
		);
	}
}