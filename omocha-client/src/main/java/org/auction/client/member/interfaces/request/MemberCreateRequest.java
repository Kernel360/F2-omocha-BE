package org.auction.client.member.interfaces.request;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
	@NotBlank
	String loginId,

	@NotBlank
	String password
) {
}
