package org.auction.domain.chat.infrastructure;

import java.util.List;

import org.auction.domain.chat.domain.entity.ChatEntity;
import org.auction.domain.chat.infrastructure.custom.ChatRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long>, ChatRepositoryCustom {

	List<ChatEntity> findAllByChatRoom_ChatRoomId(Long roomId);
}