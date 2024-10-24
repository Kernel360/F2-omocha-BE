package org.omocha.infra.querydsl;

import org.omocha.domain.chat.ChatRoomInfoDto;
import org.omocha.domain.member.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRoomRepositoryCustom {

	Slice<ChatRoomInfoDto> findChatRoomsByUser(MemberEntity memberEntity, Pageable pageable);
}
