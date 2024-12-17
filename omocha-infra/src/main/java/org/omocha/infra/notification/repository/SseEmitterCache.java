package org.omocha.infra.notification.repository;

import java.util.List;
import java.util.Map;

import org.omocha.domain.notification.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterCache {

	List<SseEmitter> getSseEmitterList(Long memberId);

	Map<String, Notification> getEventCacheList(Long memberId);

	void storeSseEmitter(Long memberId, String emitterId, SseEmitter emitter, Long EXPIRATION);

	void storeNotificationCache(String eventId, Notification notification);

	void removeSseEmitter(Long memberId, String emitterId);
}
