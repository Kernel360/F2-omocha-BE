package org.omocha.infra.auth;

import org.omocha.infra.auth.repository.TokenRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCacheReaderImpl implements TokenCacheReader {

	public final TokenRepository tokenRepository;

	@Override
	public String findValue(String key) {

		return tokenRepository.findValue(key);
	}

}
