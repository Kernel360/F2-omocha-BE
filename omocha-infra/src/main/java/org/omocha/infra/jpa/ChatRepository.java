package org.omocha.infra.jpa;

import java.util.List;

import org.omocha.domain.chat.ChatEntity;
import org.omocha.infra.querydsl.ChatRepositoryCustom;
import org.omocha.infra.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long>, ChatRepositoryCustom {

	List<ChatEntity> findAllByChatRoom_ChatRoomId(Long roomId);
}