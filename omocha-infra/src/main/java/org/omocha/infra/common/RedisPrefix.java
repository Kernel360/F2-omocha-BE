package org.omocha.infra.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisPrefix {

	AUTHCODE_PREFIX("AuthCode:"),

	REDISSON_LOCK_PREFIX("RedisLock:");

	private final String prefix;

}
