package org.omocha.infra.auth;

import org.omocha.infra.auth.repository.TokenCacheRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCacheReaderImpl implements TokenCacheReader {

	public final TokenCacheRepository tokenRepository;

	@Override
	public String findValue(String key) {

		return tokenRepository.findValue(key);
	}

}
