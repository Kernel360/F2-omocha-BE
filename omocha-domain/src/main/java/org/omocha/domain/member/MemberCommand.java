package org.omocha.domain.member;

import java.time.LocalDate;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.Password;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.domain.review.rating.Rating;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

public class MemberCommand {

	public record AddMember(
		Email email,
		Password encryptedPassword
	) {
		public Member toEntity(String randomNickname) {
			return Member.builder()
				.email(email)
				.password(encryptedPassword)
				.averageRating(new Rating(0.0))
				.nickname(randomNickname)
				.role(Role.ROLE_USER)
				.memberStatus(Member.MemberStatus.ACTIVATE)
				.emailVerified(true)
				.build();
		}
	}

	public record LoginMember(
		Email email,
		String password
	) {
	}

	public record ReissueToken(
		String refreshToken
	) {
	}

	@Builder
	public record OAuthProvider(
		String provider,
		String providerId
	) {
	}

	public record ModifyMyInfo(
		Long memberId,
		String nickname,
		PhoneNumber phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth
	) {
	}

	public record ModifyPassword(
		Long memberId,
		String currentPassword,
		Password newEncryptedPassword
	) {
	}

	public record ModifyProfileImage(
		Long memberId,
		MultipartFile profileImage
	) {
	}
}
