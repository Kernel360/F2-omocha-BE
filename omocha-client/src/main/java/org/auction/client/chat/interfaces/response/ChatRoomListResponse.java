package org.auction.client.chat.interfaces.response;

import java.util.List;

import org.auction.domain.chat.domain.dto.ChatRoomInfoDto;

public record ChatRoomListResponse(
	List<ChatRoomInfoDto> chatRooms
) {

}
