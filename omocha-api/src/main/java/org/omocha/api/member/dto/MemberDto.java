package org.omocha.api.member.dto;

import java.time.LocalDate;

import org.omocha.domain.common.Role;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MemberDto {

	public record CurrentMemberInfoResponse(
		Long memberId,
		String email,
		String userName,
		String nickName,
		String phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		String profileImageUrl,
		String loginType,
		int likeCount
	) {
	}

	public record MemberModifyRequest(
		String nickName,
		String phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth
	) {
	}

	public record MemberModifyResponse(
		Long memberId,
		String email,
		String userName,
		String nickName,
		String phoneNumber,
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
