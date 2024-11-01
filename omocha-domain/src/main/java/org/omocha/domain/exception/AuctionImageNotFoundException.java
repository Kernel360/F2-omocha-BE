package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class AuctionImageNotFoundException extends AuctionException {
	public AuctionImageNotFoundException(Long memberId) {
		super(ErrorCode.AUCTION_IMAGE_NOT_FOUND, "회원이 등록한 경매 이미지가 없습니다. memberId : " + memberId);
	}
}
