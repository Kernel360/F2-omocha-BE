package org.omocha.domain.chat;

import org.omocha.domain.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat")
public class Chat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatId;

	@Enumerated(EnumType.STRING)
	private MessageType messageType;

	private Long senderMemberId;

	private String message;

	private Long roomId;

	@Getter
	@RequiredArgsConstructor
	public enum MessageType {
		ENTER("입장"),
		CHAT("대화"),
		LEAVE("나가기");

		private final String description;
	}

	@Builder
	public Chat(
		MessageType messageType,
		Long senderMemberId,
		String message,
		Long roomId
	) {
		this.messageType = messageType;
		this.senderMemberId = senderMemberId;
		this.message = message;
		this.roomId = roomId;
	}
}
