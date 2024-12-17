package org.omocha.domain.notification;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationReader {
	List<SseEmitter> getEmitterList(Long memberId);

	Notification getNotification(Long notificationId);

	Map<String, Notification> getLostNotificationList(Long memberId);

	List<Notification> getNotReadNotificationList(Long memberId);
}
