package org.auction.infra.custom;

import org.auction.domain.chat.ChatRoomInfoDto;
import org.auction.domain.member.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRoomRepositoryCustom {

	Slice<ChatRoomInfoDto> findChatRoomsByUser(MemberEntity memberEntity, Pageable pageable);
}
