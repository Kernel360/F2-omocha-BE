package org.omocha.infra.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisPrefix {

	AUTHCODE_PREFIX("AuthCode:"),

	REDISSON_LOCK_PREFIX("RedisLock:"),

	HIGHEST_BID_PREFIX("HighestBid:"),

	TOKEN_PREFIX("RefreshToken:");

	private final String prefix;

}
