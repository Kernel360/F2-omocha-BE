package org.auction.client.member.interfaces.request;

import jakarta.validation.constraints.NotBlank;

// TODO: 회원가입 로직을 확정하고 수정해야함
public record MemberCreateRequest(
	@NotBlank
	String loginId,

	@NotBlank
	String password
) {
}
