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

	private Long buyerId;

	private Long sellerId;

	private Long auctionId;

	@Builder
	public ChatRoom(
		String roomName,
		Long buyerId,
		Long sellerId,
		Long auctionId
	) {
		this.roomName = roomName;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.auctionId = auctionId;
	}

	public boolean validateParticipant(Long memberId) {
		return memberId != null &&
			(buyerId != null && buyerId.equals(memberId) ||
				sellerId != null && sellerId.equals(memberId));
	}
}
