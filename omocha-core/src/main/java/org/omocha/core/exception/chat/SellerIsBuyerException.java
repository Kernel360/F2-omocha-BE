package org.omocha.core.exception.chat;

import org.omocha.client.common.code.ChatCode;

public class SellerIsBuyerException extends ChatException {
	public SellerIsBuyerException(
		ChatCode chatCode
	) {
		super(chatCode);
	}

	public SellerIsBuyerException(
		ChatCode chatCode,
		String detailMessage
	) {
		super(chatCode, detailMessage);
	}
}
