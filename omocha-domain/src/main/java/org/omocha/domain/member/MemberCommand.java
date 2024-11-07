package org.omocha.domain.member;

import org.springframework.web.multipart.MultipartFile;

import org.omocha.domain.auction.review.Rating;

import lombok.Builder;

public class MemberCommand {
	public record AddMember(
		String email,
		String password
	) {
		// TODO : 사용자 정보 확정 후 추가 수정 필요
		public Member toEntity() {
			return Member.builder()
				.email(email)
				.password(password)
				.averageRating(new Rating(0d))
				.role(Role.ROLE_USER)
				.userStatus(UserStatus.ACTIVATE)
				.build();
		}

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

	public record ModifyBasicInfo(
		Long memberId,
		String nickName,
		String phoneNumber
	) {
	}

	public record ModifyPassword(
		Long memberId,
		String currentPassword,
		String newPassword
	) {
	}

	public record ModifyProfileImage(
		Long memberId,
		MultipartFile profileImage
	) {

	}
}
