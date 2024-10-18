package org.auction.client.mypage.interfaces.response;

import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.domain.enums.Role;

public record MemberInfoResponse(
	// TODO : 회원 가입 정보 추가 후 변경
	Long memberId,
	String email,
	String userName,
	String nickName,
	String phoneNumber,
	String birth,
	Role role,
	String profileImageUrl
) {
	public static MemberInfoResponse toDto(
		MemberEntity memberEntity
	) {
		return new MemberInfoResponse(
			memberEntity.getMemberId(),
			memberEntity.getEmail(),
			memberEntity.getUsername(),
			memberEntity.getNickname(),
			memberEntity.getPhoneNumber(),
			memberEntity.getBirth(),
			memberEntity.getRole(),
			memberEntity.getProfileImageUrl()

		);

	}
}
