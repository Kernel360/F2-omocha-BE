package org.omocha.domain.member.exception.oauth;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

import lombok.Getter;

@Getter
public class OauthException extends OmochaException {
	private final ErrorCode errorCode;
	private final String message;

	public OauthException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		this.errorCode = errorCode;
		this.message = message;
	}
}