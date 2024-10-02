package org.auction.client.exception.jwt;

import org.auction.client.common.code.JwtCode;

public class JwtTokenNotFoundException extends JwtTokenException {
	public JwtTokenNotFoundException(
		JwtCode jwtCode
	) {
		super(jwtCode);
	}

	public JwtTokenNotFoundException(
		JwtCode jwtCode,
		String detailMessage
	) {
		super(jwtCode, detailMessage);
	}
}
