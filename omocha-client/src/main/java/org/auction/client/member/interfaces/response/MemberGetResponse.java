package org.auction.client.member.interfaces.response;

import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.domain.enums.Role;

public record MemberGetResponse(
	String loginId,
	String password,
	String nickname,
	String birth,
	String email,
	String phoenNumber,
	String imageUrl,
	Role role
) {
	public static MemberGetResponse toDto(
		MemberEntity memberEntity
	) {
		return new MemberGetResponse(
			memberEntity.getLoginId(),
			memberEntity.getPassword(),
			memberEntity.getNickname(),
			memberEntity.getBirth(),
			memberEntity.getEmail(),
			memberEntity.getPhoneNumber(),
			memberEntity.getProfileImageUrl(),
			memberEntity.getRole()
		);
	}
}
