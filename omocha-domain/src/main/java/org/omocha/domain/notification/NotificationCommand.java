package org.omocha.domain.notification;

public class NotificationCommand {
	public record Connect(
		Long memberId,
		String lastEventId
	) {

	}

	public record Read(
		Long memberId,
		Long notificationId
	) {

	}
}
