package org.omocha.domain.notification;

import java.util.UUID;

import org.omocha.domain.notification.enums.EventName;
import org.omocha.domain.notification.enums.NotificationCode;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationStore {
	void emitterStore(Long memberId, UUID emitterId, SseEmitter emitter);

	void emitterDelete(Long memberId, UUID emitterId);

	Notification notificationStore(
		Long memberId,
		EventName eventName,
		NotificationCode notificationCode,
		String data
	);
}
