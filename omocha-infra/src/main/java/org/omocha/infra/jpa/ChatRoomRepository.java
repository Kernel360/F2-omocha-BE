package org.omocha.infra.jpa;

import java.util.List;

import org.omocha.domain.chat.ChatRoomEntity;
import org.omocha.infra.querydsl.ChatRoomRepositoryCustom;
import org.omocha.domain.member.MemberEntity;
import org.omocha.infra.entity.ChatRoomEntity;
import org.omocha.infra.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>, ChatRoomRepositoryCustom {
	// 특정 경매 ID로 채팅방의 존재 여부 확인
	boolean existsByAuctionId(Long auctionId);

	// 특정 회원이 참여한 모든 채팅방 조회
	List<ChatRoomEntity> findByBuyerOrSeller(MemberEntity buyer, MemberEntity seller);

}
