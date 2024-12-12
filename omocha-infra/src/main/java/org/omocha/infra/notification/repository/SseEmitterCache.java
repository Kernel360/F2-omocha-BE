package org.omocha.infra.notification.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterCache {

	List<SseEmitter> getSseEmitterList(Long memberId);

	void storeSseEmitter(Long memberId, UUID emitterId, SseEmitter emitter);

	void removeSseEmitter(Long memberId, UUID emitterId);
}
