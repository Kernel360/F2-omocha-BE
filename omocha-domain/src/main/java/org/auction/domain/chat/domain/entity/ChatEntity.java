package org.auction.domain.chat.domain.entity;

import org.auction.domain.chat.domain.enums.MessageType;
import org.auction.domain.common.domain.entity.TimeTrackableEntity;
import org.auction.domain.member.domain.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "chat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatEntity extends TimeTrackableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_id")
	private Long chatId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MessageType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = false)
	private MemberEntity sender;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String message;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room_id", nullable = false)
	private ChatRoomEntity chatRoom;

	@Builder
	public ChatEntity(
		ChatRoomEntity chatRoom,
		MemberEntity sender,
		String message,
		MessageType type
	) {
		this.chatRoom = chatRoom;
		this.sender = sender;
		this.message = message;
		this.type = type;
	}

}
