package org.auction.client.member.interfaces.request;

import jakarta.validation.constraints.NotBlank;

public record MemberDuplicateRequest(
	@NotBlank
	String email
) {
}