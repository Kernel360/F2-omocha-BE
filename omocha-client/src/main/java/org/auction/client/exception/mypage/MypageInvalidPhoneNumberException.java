package org.auction.client.exception.mypage;

import org.auction.client.common.code.MypageCode;

public class MypageInvalidPhoneNumberException extends MypageException {
	public MypageInvalidPhoneNumberException(
		MypageCode mypageCode
	) {
		super(mypageCode);
	}

	public MypageInvalidPhoneNumberException(
		MypageCode mypageCode,
		String detailMessage
	) {
		super(mypageCode, detailMessage);
	}
}
