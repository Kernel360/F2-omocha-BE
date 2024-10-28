package org.omocha.domain.exception;

import org.omocha.domain.exception.code.OauthCode;

public class UnsupportedOAuthProviderException extends OauthException {
	public UnsupportedOAuthProviderException(
		OauthCode oauthCode
	) {
		super(oauthCode);
	}

	public UnsupportedOAuthProviderException(
		OauthCode oauthCode,
		String detailMessage
	) {
		super(oauthCode, detailMessage);
	}
}
