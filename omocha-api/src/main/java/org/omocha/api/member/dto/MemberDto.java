package org.omocha.api.member.dto;

import java.time.LocalDate;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.domain.review.rating.Rating;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MemberDto {

	public record MyInfoResponse(
		Long memberId,
		Email email,
		String userName,
		String nickname,
		PhoneNumber phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		String profileImageUrl,
		Rating averageRating,
		String loginType,
		int likeCount,
		boolean emailVerified
		
	) {
	}

	public record MemberInfoResponse(
		Long memberId,
		String nickname,
		String profileImageUrl,
		Rating averageRating
	) {
	}

	public record MyInfoModifyRequest(
		@NotBlank String nickname,
		@NotBlank String phoneNumber,
		@NotNull @JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth
	) {
	}

	public record MyInfoModifyResponse(
		Long memberId,
		Email email,
		String userName,
		String nickname,
		PhoneNumber phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		Role role,
		String profileImageUrl
	) {
	}

	public record PasswordModifyRequest(
		@NotBlank String currentPassword,
		@NotBlank String newPassword
	) {
	}

	public record ProfileImageModifyResponse(
		String imageUrl
	) {

	}

}
