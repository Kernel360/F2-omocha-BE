package org.omocha.api.auth.jwt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenManager {

	protected static final Map<String, Long> refreshTokens = new ConcurrentHashMap<>();

	public static Long findMemberIdByRefreshToken(String refreshToken) {
		return refreshTokens.get(refreshToken);
	}

	public static void putRefreshToken(String refreshToken, Long memberId) {
		refreshTokens.put(refreshToken, memberId);
	}

	private static void removeRefreshToken(String refreshToken) {
		refreshTokens.remove(refreshToken);
	}

	public static void removeUserRefreshToken(Long memberId) {
		if (memberId == null) {
			return;
		}

		for (Map.Entry<String, Long> entry : refreshTokens.entrySet()) {
			if (entry.getValue().equals(memberId)) {
				removeRefreshToken(entry.getKey());
			}
		}
	}

}