package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class LikeCountNegativeException extends AuctionException {

	public LikeCountNegativeException() {
		super(
			ErrorCode.LIKE_NOT_NEGATIVE,
			"좋아요 수는 음수가 될 수 없습니다."
		);
	}
}
