package org.omocha.infra.mail;

import java.util.Optional;

import org.omocha.domain.mail.AuthCode;
import org.omocha.domain.mail.CodeCacheReader;
import org.omocha.infra.common.RedisPrefix;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class CodeCacheReaderImpl implements CodeCacheReader {

	private final RedisTemplate<String, AuthCode> template;

	public CodeCacheReaderImpl(@Qualifier("redisTemplateForAuthCode") RedisTemplate<String, AuthCode> template) {
		this.template = template;
	}

	@Override
	public Optional<AuthCode> findCode(String key) {
		return Optional.ofNullable(template.opsForValue().get(appendPrefixKey(key)));
	}

	@Override
	public Optional<Long> findCodeDuration(String key) {
		return Optional.ofNullable(template.getExpire(appendPrefixKey(key)));
	}

	private String appendPrefixKey(String key) {
		return RedisPrefix.AUTHCODE_PREFIX.getPrefix() + key;
	}

}
