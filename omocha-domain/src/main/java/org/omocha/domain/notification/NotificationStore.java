package org.omocha.domain.notification;

import java.util.List;

import org.omocha.domain.notification.enums.EventName;
import org.omocha.domain.notification.enums.NotificationCode;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationStore {
	void emitterStore(Long memberId, String emitterId, SseEmitter emitter, Long EXPIRATION);

	void emitterDelete(Long memberId, String emitterId);

	Notification store(
		Long memberId,
		String eventId,
		EventName eventName,
		NotificationCode notificationCode,
		String data
	);

	void bulkRead(Long memberId, List<Long> notificationIdList);
}
