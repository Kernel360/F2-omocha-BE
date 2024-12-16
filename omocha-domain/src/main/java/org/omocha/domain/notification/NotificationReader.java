package org.omocha.domain.notification;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationReader {
	SseEmitter getEmitter(Long memberId);

	Notification getNotification(Long notificationId);
}
