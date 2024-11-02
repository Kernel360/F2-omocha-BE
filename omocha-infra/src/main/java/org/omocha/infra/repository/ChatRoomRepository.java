package org.omocha.infra.repository;

import org.omocha.domain.auction.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

	boolean existsByAuctionId(Long auctionId);

}
