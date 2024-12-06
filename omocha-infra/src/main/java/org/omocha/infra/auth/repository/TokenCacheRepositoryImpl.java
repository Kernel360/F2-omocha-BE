package org.omocha.infra.auth.repository;

import static org.omocha.infra.common.RedisPrefix.*;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenCacheRepositoryImpl implements TokenCacheRepository {

	private static final int DURATION = 1;

	private final StringRedisTemplate redisTemplate;

	@Override
	public String findValue(String key) {

		return redisTemplate.opsForValue().get(appendPrefix(key));
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
