package org.omocha.domain.notification;

import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.member.Member;
import org.omocha.domain.notification.enums.EventName;
import org.omocha.domain.notification.enums.NotificationCode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	private EventName eventName;

	@Enumerated(EnumType.STRING)
	private NotificationCode notificationCode;

	private String data;

	private boolean read;

	@Builder
	public Notification(
		long notificationId,
		Member member,
		EventName eventName,
		NotificationCode notificationCode,
		String data,
		boolean read
	) {
		this.notificationId = notificationId;
		this.member = member;
		this.eventName = eventName;
		this.notificationCode = notificationCode;
		this.data = data;
		this.read = read;
	}

	public void modifyAsRead() {
		read = true;
	}
}
