package org.omocha.domain.member.exception.oauth;

import org.omocha.domain.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class OauthException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;

	public OauthException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
}