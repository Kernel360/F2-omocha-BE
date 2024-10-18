package org.auction.client.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtCategory {
	ACCESS("access"),
	REFRESH("refresh");

	private final String value;
}
