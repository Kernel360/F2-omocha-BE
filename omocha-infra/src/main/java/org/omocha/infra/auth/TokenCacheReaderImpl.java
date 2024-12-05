package org.omocha.infra.auth;

import static org.omocha.infra.common.RedisPrefix.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenCacheReaderImpl implements TokenCacheReader {

	private final RedisTemplate<String, String> redisTemplate;

	TokenCacheReaderImpl(@Qualifier("redisTemplateForToken") RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public String findValue(String key) {

		return redisTemplate.opsForValue().get(appendPrefix(key));
	}

	private String appendPrefix(String key) {
		return TOKEN_PREFIX.getPrefix() + key;

	}

}
