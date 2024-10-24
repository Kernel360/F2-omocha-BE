package org.omocha.core.exception.oauth;

import org.omocha.client.common.code.OauthCode;

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
