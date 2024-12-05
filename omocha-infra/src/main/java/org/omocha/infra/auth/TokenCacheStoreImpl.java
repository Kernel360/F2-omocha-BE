package org.omocha.infra.auth;

import static org.omocha.infra.common.RedisPrefix.*;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCacheStoreImpl implements TokenCacheStore {

	private static final int DURATION = 1;

	private final StringRedisTemplate redisTemplate;

	@Override
	public void storeKey(String key, Long value) {

		redisTemplate.opsForValue().set(appendPrefix(key), value.toString(), Duration.ofDays(DURATION));
	}

	@Override
	public void deleteKey(String key) {
		redisTemplate.delete(appendPrefix(key));
	}

	private String appendPrefix(String key) {
		return TOKEN_PREFIX.getPrefix() + key;
	}
}
