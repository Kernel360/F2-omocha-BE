package org.omocha.infra.auth;

import org.omocha.infra.auth.repository.TokenCacheRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCacheStoreImpl implements TokenCacheStore {

	private final TokenCacheRepository tokenRepository;

	@Override
	public void storeKey(String key, Long value) {
		tokenRepository.storeKey(key, value);
	}

	@Override
	public void deleteKey(String key) {
		tokenRepository.deleteKey(key);
	}
}
