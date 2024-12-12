package org.omocha.infra.notification.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
	private final ConcurrentHashMap<UUID, SseEmitter> emitterMap = new ConcurrentHashMap<>();

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
			sseEmitters.add(emitterMap.get(UUID.fromString(emitterId)));
		});

		return sseEmitters;
	}

	@Override
	public void storeSseEmitter(Long memberId, UUID emitterId, SseEmitter emitter) {
		emitterMap.put(emitterId, emitter);
		template.opsForSet().add(RedisPrefix.SSE_EMITTER_PREFIX.getPrefix() + memberId, emitterId.toString());
	}

	@Override
	public void removeSseEmitter(Long memberId, UUID emitterId) {
		emitterMap.remove(emitterId);
		template.opsForSet().remove(RedisPrefix.SSE_EMITTER_PREFIX.getPrefix() + memberId, emitterId.toString());
	}

	// TODO: 비어있는 memberId 삭제하는 로직 필요
}
