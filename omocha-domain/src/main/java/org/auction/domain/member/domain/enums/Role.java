package org.auction.domain.member.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

	private final String role;
}
