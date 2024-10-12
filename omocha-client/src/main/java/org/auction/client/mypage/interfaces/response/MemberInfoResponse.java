package org.auction.client.mypage.interfaces.response;

import org.auction.domain.member.domain.entity.MemberEntity;

public record MemberInfoResponse(
	Long memberId
) {
	public static MemberInfoResponse toDto(
		MemberEntity memberEntity
	) {
		return new MemberInfoResponse(
			memberEntity.getMemberId()
		);

	}
}
