package org.omocha.infra.chat.repository;

import org.omocha.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

	boolean existsByAuctionId(Long auctionId);

}
