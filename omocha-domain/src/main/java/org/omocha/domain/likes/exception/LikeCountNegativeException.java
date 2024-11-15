package org.omocha.domain.likes.exception;

import org.omocha.domain.auction.exception.AuctionException;
import org.omocha.domain.common.code.ErrorCode;

public class LikeCountNegativeException extends AuctionException {

	public LikeCountNegativeException() {
		super(
			ErrorCode.LIKE_NOT_NEGATIVE,
			"찜 수는 음수가 될 수 없습니다."
		);
	}
}
