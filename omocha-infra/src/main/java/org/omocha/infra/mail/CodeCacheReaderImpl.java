package org.omocha.infra.mail;

import java.util.Optional;

import org.omocha.domain.mail.AuthCode;
import org.omocha.domain.mail.CodeCacheReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.Getter;

@Getter
@Repository
public class CodeCacheReaderImpl implements CodeCacheReader {

	private static final String PREFIX = "AuthCode :";

	private final RedisTemplate<String, AuthCode> template;

	public CodeCacheReaderImpl(@Qualifier("redisTemplateForAuthCode") RedisTemplate<String, AuthCode> template) {
		this.template = template;
	}

	@Override
	public Optional<AuthCode> findCode(String key) {
		return Optional.ofNullable(template.opsForValue().get(setPrefix() + key));

	}

	public String setPrefix() {
		return PREFIX;
	}

}
