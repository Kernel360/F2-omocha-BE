package org.omocha.api.member.dto;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.domain.review.rating.Rating;

public class MemberDto {

	public record MyInfoResponse(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		Email email,
		String userName,
		String nickName,
		PhoneNumber phoneNumber,
		String birth,
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
		String phoneNumber
	) {
	}

	public record MyInfoModifyResponse(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		Email email,
		String userName,
		String nickName,
		PhoneNumber phoneNumber,
		String birth,
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
