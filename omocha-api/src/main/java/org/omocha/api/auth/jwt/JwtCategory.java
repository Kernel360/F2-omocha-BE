package org.omocha.api.auth.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtCategory {
	ACCESS("access"),
	REFRESH("refresh");

	private final String value;
}