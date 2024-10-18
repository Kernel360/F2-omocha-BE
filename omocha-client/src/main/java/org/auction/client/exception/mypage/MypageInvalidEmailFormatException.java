package org.auction.client.exception.mypage;

import org.auction.client.common.code.MypageCode;

public class MypageInvalidEmailFormatException extends MypageException {
	public MypageInvalidEmailFormatException(
		MypageCode mypageCode
	) {
		super(mypageCode);
	}

	public MypageInvalidEmailFormatException(
		MypageCode mypageCode,
		String detailMessage
	) {
		super(mypageCode, detailMessage);
	}
}
