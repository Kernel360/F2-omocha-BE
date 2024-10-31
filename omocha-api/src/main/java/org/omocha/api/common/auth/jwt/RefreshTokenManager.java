package org.omocha.api.common.auth.jwt;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.omocha.domain.exception.InvalidRefreshTokenException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenManager {

	protected static final Map<String, Long> refreshTokens = new ConcurrentHashMap<>();

	// TODO: exception에 refresh token 넘겨주는거 확인 필요(프론트에 토큰 노출 위험, 잘못된 토큰이니깐 문제 없나...?)
	public static Long findMemberIdByRefreshToken(String refreshToken) {
		return Optional.ofNullable(refreshTokens.get(refreshToken))
			.orElseThrow(() -> new InvalidRefreshTokenException(refreshToken));
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