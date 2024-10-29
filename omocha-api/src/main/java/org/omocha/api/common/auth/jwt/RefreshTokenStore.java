package org.omocha.api.common.auth.jwt;

import static org.omocha.domain.exception.code.JwtCode.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.omocha.domain.exception.InvalidRefreshTokenException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenStore {

	protected static final Map<String, Long> refreshTokens = new ConcurrentHashMap<>();

	public static Long findMemberIdByRefreshToken(String refreshToken) {
		return Optional.ofNullable(refreshTokens.get(refreshToken))
			.orElseThrow(() -> new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN));
	}

	public static void putRefreshToken(String refreshToken, Long memberId) {
		refreshTokens.put(refreshToken, memberId);
	}

	private static void removeRefreshToken(String refreshToken) {
		refreshTokens.remove(refreshToken);
	}

	public static void removeUserRefreshToken(Long memberId) {
		for (Map.Entry<String, Long> entry : refreshTokens.entrySet()) {
			if (entry.getValue().equals(memberId)) {
				removeRefreshToken(entry.getKey());
			}
		}
	}

}