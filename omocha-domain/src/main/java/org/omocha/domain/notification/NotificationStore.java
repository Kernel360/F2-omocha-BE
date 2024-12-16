package org.omocha.domain.notification;

import org.omocha.domain.notification.enums.EventName;
import org.omocha.domain.notification.enums.NotificationCode;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationStore {
	void emitterStore(Long memberId, SseEmitter emitter);

	void emitterDelete(Long memberId);

	Notification notificationStore(
		Long memberId,
		EventName eventName,
		NotificationCode notificationCode,
		String data
	);
}
