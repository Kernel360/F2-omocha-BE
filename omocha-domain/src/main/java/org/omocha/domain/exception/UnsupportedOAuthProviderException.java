package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class UnsupportedOAuthProviderException extends OauthException {
	public UnsupportedOAuthProviderException(String provider) {
		super(
			ErrorCode.INVALID_BID_UNIT,
			"지원하지 않는 OAuth Provider입니다. provider: " + provider
		);
	}
}
