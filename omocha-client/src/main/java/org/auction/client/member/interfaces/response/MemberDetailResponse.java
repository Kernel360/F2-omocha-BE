package org.auction.client.member.interfaces.response;

import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.domain.enums.Role;

// TODO: 넘겨줄 정보들에 대해 추후 정해야함, 일단은 Password 제외하고 다 넘겨줌
public record MemberDetailResponse(
	String email,
	String nickname,
	String birth,
	String phoneNumber,
	String imageUrl,
	Role role
) {
	public static MemberDetailResponse toDto(
		MemberEntity memberEntity
	) {
		return new MemberDetailResponse(
			memberEntity.getEmail(),
			memberEntity.getNickname(),
			memberEntity.getBirth(),
			memberEntity.getPhoneNumber(),
			memberEntity.getProfileImageUrl(),
			memberEntity.getRole()
		);
	}
}
