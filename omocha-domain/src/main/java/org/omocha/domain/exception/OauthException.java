package org.omocha.domain.exception;

import org.omocha.domain.exception.code.OauthCode;

import lombok.Getter;

@Getter
public class OauthException extends RuntimeException {
	private final OauthCode oauthCode;
	private final String detailMessage;

	public OauthException(
		OauthCode oauthCode
	) {
		super(oauthCode.getResultMsg());
		this.oauthCode = oauthCode;
		this.detailMessage = oauthCode.getResultMsg();
	}

	public OauthException(
		OauthCode oauthCode,
		String detailMessage
	) {
		super(oauthCode.getResultMsg());
		this.oauthCode = oauthCode;
		this.detailMessage = detailMessage;
	}
}