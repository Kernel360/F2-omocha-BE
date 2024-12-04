package org.omocha.infra.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisPrefix {

	AUTHCODE_PREFIX("AuthCode:");

	private final String prefix;

}
