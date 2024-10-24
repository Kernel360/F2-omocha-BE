package org.omocha.core.exception.jwt;

import org.omocha.client.common.code.JwtCode;

import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException {
	private final JwtCode jwtCode;
	private final String detailMessage;

	public JwtTokenException(
		JwtCode jwtCode
	) {
		super(jwtCode.getResultMsg());
		this.jwtCode = jwtCode;
		this.detailMessage = jwtCode.getResultMsg();
	}

	public JwtTokenException(
		JwtCode jwtCode,
		String detailMessage
	) {
		super(jwtCode.getResultMsg());
		this.jwtCode = jwtCode;
		this.detailMessage = detailMessage;
	}
}
