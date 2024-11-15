package org.omocha.domain.bid.exception;

import org.omocha.domain.common.code.ErrorCode;

public class SelfBidNotAllowedException extends BidException {
	public SelfBidNotAllowedException(Long auctionId, Long memberId) {
		super(
			ErrorCode.SELF_BID_NOT_ALLOWED,
			"자신의 경매에 입찰을 걸 수 없습니다. auctionId: " + auctionId + ", memberId: " + memberId
		);
	}
}