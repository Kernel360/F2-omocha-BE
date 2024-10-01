package org.auction.client.jwt;

import static org.auction.client.common.code.JwtCode.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.auction.client.exception.jwt.InvalidRefreshTokenException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

	protected static final Map<String, Long> refreshTokens = new HashMap<>();

	public static Long findMemberIdByRefreshToken(final String refreshToken) {
		return Optional.ofNullable(refreshTokens.get(refreshToken))
			.orElseThrow(() -> new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN));
	}

	public static void putRefreshToken(final String refreshToken, Long memberId) {
		refreshTokens.put(refreshToken, memberId);
	}

	private static void removeRefreshToken(final String refreshToken) {
		refreshTokens.remove(refreshToken);
	}

	public static void removeUserRefreshToken(final Long memberId) {
		for (Map.Entry<String, Long> entry : refreshTokens.entrySet()) {
			if (entry.getValue().equals(memberId)) {
				removeRefreshToken(entry.getKey());
			}
		}
	}

}
