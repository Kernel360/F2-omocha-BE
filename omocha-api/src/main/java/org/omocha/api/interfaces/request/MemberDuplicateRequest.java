package org.omocha.api.interfaces.request;

import jakarta.validation.constraints.NotBlank;

public record MemberDuplicateRequest(
	@NotBlank
	String email
) {
}