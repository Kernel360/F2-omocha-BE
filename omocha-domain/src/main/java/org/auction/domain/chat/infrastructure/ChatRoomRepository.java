package org.auction.domain.chat.infrastructure;

import java.util.List;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
	// 특정 채팅방 이름으로 채팅방의 존재 여부 확인
	boolean existsByRoomName(String roomName);

	// 특정 회원이 참여한 모든 채팅방 조회
	List<ChatRoomEntity> findByBuyerOrSeller(MemberEntity buyer, MemberEntity seller);

	// 특정 경매와 구매자에 대한 채팅 존재 유무 확인
	boolean existsByAuctionEntityAndBuyer(AuctionEntity auctionEntity, MemberEntity buyer);
}
