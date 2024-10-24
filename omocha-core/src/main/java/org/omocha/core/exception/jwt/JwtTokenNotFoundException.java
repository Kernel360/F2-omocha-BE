package org.omocha.core.exception.jwt;

import org.omocha.client.common.code.JwtCode;

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
