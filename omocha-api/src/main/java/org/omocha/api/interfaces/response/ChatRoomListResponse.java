package org.omocha.api.interfaces.response;

import java.util.List;

import org.omocha.domain.auction.chat.ChatRoomInfoDto;

public record ChatRoomListResponse(
	List<ChatRoomInfoDto> chatRooms
) {

}
