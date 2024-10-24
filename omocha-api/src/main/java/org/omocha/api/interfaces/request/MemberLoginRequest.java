package org.omocha.api.interfaces.request;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
	@NotBlank
	String email,

	@NotBlank
	String password
) {
}
