package org.omocha.infra.auth;

import static org.omocha.infra.common.RedisPrefix.*;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCacheReaderImpl implements TokenCacheReader {

	private final StringRedisTemplate redisTemplate;

	@Override
	public String findValue(String key) {

		return redisTemplate.opsForValue().get(appendPrefix(key));
	}

	private String appendPrefix(String key) {
		return TOKEN_PREFIX.getPrefix() + key;

	}

}
