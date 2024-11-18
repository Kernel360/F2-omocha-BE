package org.omocha.domain.category.exception;

import org.omocha.domain.auction.exception.AuctionException;
import org.omocha.domain.common.code.ErrorCode;

public class CategoryNotFoundException extends AuctionException {
	public enum Type {
		CATEGORY_ID,
		AUCTION_ID
	}

	public CategoryNotFoundException(Type type, Long id) {
		super(
			ErrorCode.CATEGORY_NOT_FOUND,
			generateMessage(type, id)
		);
	}

	private static String generateMessage(Type type, Long id) {
		switch (type) {
			case CATEGORY_ID:
				return "카테고리를 찾지 못했습니다. categoryId: " + id;
			case AUCTION_ID:
				return "카테고리를 찾지 못했습니다. auctionId: " + id;
			default:
				return null;
		}
	}

}
