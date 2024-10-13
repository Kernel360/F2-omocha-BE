package org.auction.domain.chat.domain.entity;

import org.auction.domain.common.domain.entity.TimeTrackableEntity;
import org.auction.domain.member.domain.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatroom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoomEntity extends TimeTrackableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_room_id")
	private Long chatRoomId;

	@Column(name = "room_name", nullable = false)
	private String roomName;

	@Column(name = "now_price", nullable = false)
	private Long nowPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	private MemberEntity buyer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	private MemberEntity seller;

	@Column(name = "auction_id", nullable = false)
	private Long auctionId;

	@Builder
	public ChatRoomEntity(
		String roomName,
		Long nowPrice,
		MemberEntity buyer,
		MemberEntity seller,
		Long auctionId
	) {
		this.roomName = roomName;
		this.nowPrice = nowPrice;
		this.buyer = buyer;
		this.seller = seller;
		this.auctionId = auctionId;
	}

	public boolean validateParticipant(MemberEntity member) {
		return buyer.equals(member) || seller.equals(member);
	}

}