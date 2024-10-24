package org.auction.infra;

import java.util.List;

import org.auction.domain.chat.ChatEntity;
import org.auction.infra.custom.ChatRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long>, ChatRepositoryCustom {

	List<ChatEntity> findAllByChatRoom_ChatRoomId(Long roomId);
}