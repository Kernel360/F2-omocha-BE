package org.auction.client.exception.jwt;

import org.auction.client.common.code.JwtCode;

public class InvalidRefreshTokenException extends JwtTokenException {
	public InvalidRefreshTokenException(
		JwtCode jwtCode
	) {
		super(jwtCode);
	}

	public InvalidRefreshTokenException(
		JwtCode jwtCode,
		String detailMessage
	) {
		super(jwtCode, detailMessage);
	}
}
