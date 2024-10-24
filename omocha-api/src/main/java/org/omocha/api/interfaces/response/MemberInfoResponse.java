package org.omocha.api.interfaces.response;

import org.omocha.domain.member.MemberEntity;

public record MemberInfoResponse(
	// TODO : 회원 가입 정보 추가 후 변경
	Long memberId,
	String email
) {
	public static MemberInfoResponse toDto(
		MemberEntity memberEntity
	) {
		return new MemberInfoResponse(
			memberEntity.getMemberId(),
			memberEntity.getEmail()
		);

	}
}
