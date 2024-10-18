package org.auction.client.member.interfaces.request;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
	@NotBlank
	String email,

	@NotBlank
	String password
) {
}
