package org.auction.client.chat.interfaces.response;

import java.util.List;

public record ChatRoomListResponse(
	List<ChatRoomResponse> chatRooms
) {

}
