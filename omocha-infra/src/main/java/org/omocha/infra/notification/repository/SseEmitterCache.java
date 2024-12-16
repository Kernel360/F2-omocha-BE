package org.omocha.infra.notification.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterCache {

	SseEmitter getSseEmitter(Long memberId);

	void storeSseEmitter(Long memberId, SseEmitter emitter);

	void removeSseEmitter(Long memberId);
}
