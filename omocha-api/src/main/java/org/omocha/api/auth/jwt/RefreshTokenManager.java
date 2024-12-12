package org.omocha.api.auth.jwt;

import org.omocha.infra.auth.TokenCacheReader;
import org.omocha.infra.auth.TokenCacheStore;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenManager {

	public TokenCacheReader tokenCacheReader;
	public TokenCacheStore tokenCacheStore;

	public RefreshTokenManager(TokenCacheReader tokenCacheReader, TokenCacheStore tokenCacheStore) {
		this.tokenCacheReader = tokenCacheReader;
		this.tokenCacheStore = tokenCacheStore;
	}

	public Long findMemberIdByRefreshToken(String refreshToken) {

		String str = tokenCacheReader.findValue(refreshToken);

		return str != null ? Long.parseLong(str) : null;
	}

	public void putRefreshToken(String refreshToken, Long memberId) {
		tokenCacheStore.storeKey(refreshToken, memberId);
	}

	public void removeRefreshToken(String refreshToken) {
		tokenCacheStore.deleteKey(refreshToken);
	}

}