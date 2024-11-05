package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class MemberInvalidException extends AuctionException {
	public MemberInvalidException(Long memberId) {
		super(
			ErrorCode.AUCTION_MEMBER_INVALID,
			"경매를 삭제할 수 없는 사용자입니다 " + memberId
		);
	}
}
