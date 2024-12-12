package org.omocha.domain.notification;

public class NotificationCommand {
	public record Connect(
		Long memberId
	) {

	}

	public record Read(
		Long notificationId
	) {

	}
}
