package org.omocha.infra.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisPrefix {

	AUTHCODE_PREFIX("AuthCode:"),
	TOKEN_PREFIX("RefreshToken:"),
	SSE_EMITTER_PREFIX("SSEEmitter:");

	private final String prefix;

}
