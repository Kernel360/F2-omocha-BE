package org.omocha.infra.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisPrefix {

	AUTHCODE_PREFIX("AuthCode:"),
	TOKEN_PREFIX("RefreshToken:"),
	HIGHEST_BID_PREFIX("HighestBid:");

	private final String prefix;

}
