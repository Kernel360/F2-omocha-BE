package org.omocha.api.member.dto;

import java.time.LocalDate;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.domain.review.rating.Rating;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MemberDto {

	public record MyInfoResponse(
		Long memberId,
		Email email,
		String userName,
		String nickName,
		PhoneNumber phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		String profileImageUrl,
		Rating averageRating,
		String loginType,
		int likeCount
	) {
	}

	public record MemberInfoResponse(
		Long memberId,
		String nickName,
		String profileImageUrl,
		Rating averageRating
	) {
	}

	public record MyInfoModifyRequest(
		String nickName,
		String phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth
	) {
	}

	public record MyInfoModifyResponse(
		Long memberId,
		Email email,
		String userName,
		String nickName,
		PhoneNumber phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		Role role,
		String profileImageUrl
	) {
	}

	public record PasswordModifyRequest(
		String currentPassword,
		String newPassword
	) {
	}

	public record ProfileImageModifyResponse(
		String imageUrl
	) {

	}

}
