package org.omocha.api.auth.dto;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.PhoneNumber;

public class AuthDto {

	public record MemberAddRequest(
		String email,
		String password
	) {
	}

	public record MemberLoginRequest(
		String email,
		String password
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
