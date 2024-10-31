package org.omocha.domain.member;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;

public class MemberCommand {
	public record MemberCreate(
		String email,
		String password
	) {
		// TODO : 사용자 정보 확정 후 추가 수정 필요
		public Member toEntity() {
			return Member.builder()
				.email(email)
				.password(password)
				.role(Role.ROLE_USER)
				.userStatus(UserStatus.ACTIVATE)
				.build();
		}

	}

	public record MemberDuplicate(
		String email
	) {

	}

	public record MemberLogin(
		String email,
		String password
	) {

	}

	@Builder
	public record OAuthProvider(
		String provider,
		String providerId
	) {

	}

	public record MemberModify(
		Long memberId,
		String nickName,
		String phoneNumber
	) {
	}

	public record PasswordModify(
		Long memberId,
		String currentPassword,
		String newPassword
	) {
	}

	public record ProfileImageModify(
		Long memberId,
		MultipartFile profileImage
	) {

	}
}
