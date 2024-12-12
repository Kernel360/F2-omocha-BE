package org.omocha.domain.notification;

import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationReader {
	List<SseEmitter> getEmitterList(Long memberId);

	Notification getNotification(Long notificationId);
}
