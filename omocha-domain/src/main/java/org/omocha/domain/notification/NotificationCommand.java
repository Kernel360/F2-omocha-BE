package org.omocha.domain.notification;

import java.util.List;

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

	public record ReadAll(
		Long memberId,
		List<Long> notificationIdList
	) {

	}
}
