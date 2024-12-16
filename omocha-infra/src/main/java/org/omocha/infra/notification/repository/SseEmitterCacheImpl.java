package org.omocha.infra.notification.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Getter;

@Getter
@Component
public class SseEmitterCacheImpl implements SseEmitterCache {

	private final ConcurrentHashMap<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

	@Override
	public SseEmitter getSseEmitter(Long memberId) {
		return emitterMap.get(memberId);
	}

	@Override
	public void storeSseEmitter(Long memberId, SseEmitter emitter) {
		emitterMap.put(memberId, emitter);
	}

	@Override
	public void removeSseEmitter(Long memberId) {
		SseEmitter emitter = emitterMap.remove(memberId);
		emitter.complete();
	}
}
