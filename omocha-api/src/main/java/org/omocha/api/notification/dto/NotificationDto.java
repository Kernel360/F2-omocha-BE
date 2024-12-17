package org.omocha.api.notification.dto;

import java.util.List;

public class NotificationDto {

	public record ReadAll(
		List<Long> notificationIdList
	) {
	}
}
