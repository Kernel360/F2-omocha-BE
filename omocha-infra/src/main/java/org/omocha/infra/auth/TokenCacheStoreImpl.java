package org.omocha.infra.auth;

import static org.omocha.infra.common.RedisPrefix.*;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenCacheStoreImpl implements TokenCacheStore {

	private static final int DURATION = 7;

	private final RedisTemplate<String, String> redisTemplate;

	public TokenCacheStoreImpl(@Qualifier("redisTemplateForToken") RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

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
