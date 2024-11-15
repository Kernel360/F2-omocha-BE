package org.omocha.domain.member.exception;

import org.omocha.domain.auction.exception.AuctionException;
import org.omocha.domain.common.code.ErrorCode;

public class MemberInvalidException extends AuctionException {
	public MemberInvalidException(Long memberId) {
		super(
			ErrorCode.AUCTION_MEMBER_INVALID,
			"경매를 삭제할 수 없는 사용자입니다 " + memberId
		);
	}
}
