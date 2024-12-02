package org.omocha.api.auth.dto;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.PhoneNumber;

import jakarta.validation.constraints.NotBlank;

public class AuthDto {

	public record MemberAddRequest(
		@NotBlank String email,
		@NotBlank String password
	) {
	}

	public record MemberLoginRequest(
		@NotBlank String email,
		@NotBlank String password
	) {
	}

	public record TokenReissueRequest(
		@NotBlank String refreshToken
	) {
	}

	public record JwtResponse(
		String accessToken,
		String refreshToken
	) {
	}

	public record MemberDetailResponse(
		Email email,
		String nickname,
		String birth,
		PhoneNumber phoneNumber,
		String imageUrl,
		Role role
	) {
	}

}
