package org.omocha.domain.notification.exception;

import org.omocha.domain.common.code.ErrorCode;

public class NotificationNotFoundException extends NotificationException {
	public NotificationNotFoundException(Long notificationId) {
		super(
			ErrorCode.NOTIFICATION_NOT_FOUND,
			"알림을 찾을 수 없습니다. notificationId: " + notificationId
		);
	}
}
