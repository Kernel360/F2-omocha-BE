package org.omocha.infra.mail;

import java.time.Duration;

import org.omocha.domain.mail.AuthCode;
import org.omocha.domain.mail.CodeCacheStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.Getter;

@Getter
@Repository
public class CodeCacheStoreImpl implements CodeCacheStore {

	private static final String PREFIX = "AuthCode :";

	private final RedisTemplate<String, AuthCode> template;

	public CodeCacheStoreImpl(@Qualifier("redisTemplateForAuthCode") RedisTemplate<String, AuthCode> template) {
		this.template = template;
	}

	@Override
	public void storeCode(String email, AuthCode code) {
		template.opsForValue().set(setPrefix() + email, code, Duration.ofMinutes(30));
	}

	@Override
	public void deleteCode(String email) {
		template.delete(setPrefix() + email);
	}

	public String setPrefix() {
		return PREFIX;
	}

}
