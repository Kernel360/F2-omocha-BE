package org.omocha.domain.chat;

import org.omocha.domain.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chatroom")
public class ChatRoom extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomId;

	private String roomName;

	private Long buyerMemberId;

	private Long sellerMemberId;

	private Long auctionId;

	@Builder
	public ChatRoom(
		String roomName,
		Long buyerMemberId,
		Long sellerMemberId,
		Long auctionId
	) {
		this.roomName = roomName;
		this.buyerMemberId = buyerMemberId;
		this.sellerMemberId = sellerMemberId;
		this.auctionId = auctionId;
	}

	public boolean validateParticipant(Long memberId) {
		return memberId != null && (buyerMemberId.equals(memberId) || sellerMemberId.equals(memberId));
	}
}
