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

	protected static final Map<String, String> refreshTokens = new HashMap<>();

	public static String findLoginIdByRefreshToken(final String refreshToken) {
		return Optional.ofNullable(refreshTokens.get(refreshToken))
			.orElseThrow(() -> new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN));
	}

	public static void putRefreshToken(final String refreshToken, String loginId) {
		refreshTokens.put(refreshToken, loginId);
	}

	private static void removeRefreshToken(final String refreshToken) {
		refreshTokens.remove(refreshToken);
	}

	public static void removeUserRefreshToken(final String refreshToken) {
		for (Map.Entry<String, String> entry : refreshTokens.entrySet()) {
			if (entry.getValue() == refreshToken) {
				removeRefreshToken(entry.getKey());
			}
		}
	}

}
