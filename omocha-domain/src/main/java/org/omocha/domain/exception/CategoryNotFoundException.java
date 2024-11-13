package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class CategoryNotFoundException extends AuctionException {
	public CategoryNotFoundException(Long categoryId) {
		super(
			ErrorCode.CATEGORY_NOT_FOUND,
			"카테고리를 찾지 못했습니다. categoryId: {} " + categoryId
		);
	}

	public CategoryNotFoundException(Long auctionId, Long memberId) {
		super(
			ErrorCode.CATEGORY_NOT_FOUND,
			"카테고리를 찾지 못했습니다. auctionId: {}, memberId: {} " + auctionId + memberId
		);
	}
}
