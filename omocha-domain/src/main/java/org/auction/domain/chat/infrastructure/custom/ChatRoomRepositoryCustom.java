package org.auction.domain.chat.infrastructure.custom;

import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRoomRepositoryCustom {

	Slice<ChatRoomEntity> findChatRoomsByUser(MemberEntity memberEntity, Pageable pageable);
}
