package org.omocha.infra.notification.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.omocha.domain.notification.Notification;
import org.omocha.infra.common.RedisPrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Getter;

@Getter
@Component
public class SseEmitterCacheImpl implements SseEmitterCache {

	private final RedisTemplate<String, String> template;
	private final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Notification> eventCache = new ConcurrentHashMap<>();

	public SseEmitterCacheImpl(StringRedisTemplate redisTemplate) {
		this.template = redisTemplate;
	}

	@Override
	public List<SseEmitter> getSseEmitterList(Long memberId) {
		Set<String> emitterIdList = template.opsForSet().members(RedisPrefix.SSE_EMITTER_PREFIX.getPrefix() + memberId);

		if (emitterIdList == null) {
			return Collections.emptyList();
		}

		List<SseEmitter> sseEmitters = new ArrayList<>();
		emitterIdList.forEach(emitterId -> {
			sseEmitters.add(emitters.get(emitterId));
		});

		return sseEmitters;
	}

	@Override
	public Map<String, Notification> getEventCacheList(Long memberId) {
		Map<String, Notification> result = new HashMap<>();

		eventCache.entrySet().removeIf(entry -> {
			boolean matches = entry.getKey().startsWith(String.valueOf(memberId));
			if (matches) {
				result.put(entry.getKey(), entry.getValue());
			}

			return matches;
		});

		return result;
	}

	@Override
	public void storeSseEmitter(
		Long memberId,
		String emitterId,
		SseEmitter emitter,
		Long EXPIRATION
	) {
		emitters.put(emitterId, emitter);
		template.opsForSet().add(RedisPrefix.SSE_EMITTER_PREFIX.getPrefix() + memberId, emitterId);
	}

	@Override
	public void storeNotificationCache(String eventId, Notification notification) {
		eventCache.put(eventId, notification);
	}

	@Override
	public void removeSseEmitter(Long memberId, String emitterId) {
		emitters.remove(emitterId);
		template.opsForSet().remove(RedisPrefix.SSE_EMITTER_PREFIX.getPrefix() + memberId, emitterId);
	}
}
