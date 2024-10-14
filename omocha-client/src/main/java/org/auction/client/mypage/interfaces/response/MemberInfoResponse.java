package org.auction.client.mypage.interfaces.response;

import org.auction.domain.member.domain.entity.MemberEntity;

public record MemberInfoResponse(
	// TODO : 회원 가입 정보 추가 후 변경
	Long memberId,
	String loginId
) {
	public static MemberInfoResponse toDto(
		MemberEntity memberEntity
	) {
		return new MemberInfoResponse(
			memberEntity.getMemberId(),
			memberEntity.getLoginId()
		);

	}
}
