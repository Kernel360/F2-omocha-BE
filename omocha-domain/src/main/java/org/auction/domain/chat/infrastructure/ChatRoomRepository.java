package org.auction.domain.chat.infrastructure;

import java.util.List;

import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
	// 특정 채팅방 이름으로 채팅방의 존재 여부 확인
	boolean existsByRoomName(String roomName);

	// 특정 회원이 참여한 모든 채팅방 조회
	List<ChatRoomEntity> findByBuyerOrSeller(MemberEntity buyer, MemberEntity seller);

}
