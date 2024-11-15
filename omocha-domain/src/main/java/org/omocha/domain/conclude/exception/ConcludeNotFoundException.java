package org.omocha.domain.conclude.exception;

import org.omocha.domain.auction.exception.AuctionException;
import org.omocha.domain.common.code.ErrorCode;

public class ConcludeNotFoundException extends AuctionException {
	public ConcludeNotFoundException(Long auctionId) {
		super(
			ErrorCode.CONCLUDE_NOT_FOUND,
			"해당 경매에는 낙찰 내역이 존재하지 않습니다. auctionId: " + auctionId
		);
	}
}
