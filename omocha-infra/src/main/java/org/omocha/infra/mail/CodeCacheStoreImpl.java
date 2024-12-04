package org.omocha.infra.mail;

import static org.omocha.infra.common.RedisPrefix.*;

import java.time.Duration;

import org.omocha.domain.mail.AuthCode;
import org.omocha.domain.mail.CodeCacheStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class CodeCacheStoreImpl implements CodeCacheStore {

	private static final int DURATION = 30;

	private final RedisTemplate<String, AuthCode> template;

	public CodeCacheStoreImpl(@Qualifier("redisTemplateForAuthCode") RedisTemplate<String, AuthCode> template) {
		this.template = template;
	}

	@Override
	public void storeCode(String email, AuthCode code) {
		template.opsForValue().set(appendPrefixKey(email), code, Duration.ofMinutes(DURATION));
	}

	@Override
	public void deleteCode(String email) {
		template.delete(appendPrefixKey(email));
	}

	private String appendPrefixKey(String key) {
		return AUTHCODE_PREFIX.getPrefix() + key;
	}

}
