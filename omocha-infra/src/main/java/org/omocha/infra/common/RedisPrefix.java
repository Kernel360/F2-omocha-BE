package org.omocha.infra.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisPrefix {

	AUTHCODE_PREFIX("AuthCode:"),
	TOKEN_PREFIX("RefreshToken:"),
	REDISSON_LOCK_PREFIX("RedisLock:"),
	HIGHEST_BID_PREFIX("HighestBid:"),
	SSE_EMITTER_PREFIX("SSEEmitter:");

	private final String prefix;

}
