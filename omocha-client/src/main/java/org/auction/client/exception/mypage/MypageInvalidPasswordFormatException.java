package org.auction.client.exception.mypage;

import org.auction.client.common.code.MypageCode;

public class MypageInvalidPasswordFormatException extends MypageException {
	public MypageInvalidPasswordFormatException(
		MypageCode mypageCode
	) {
		super(mypageCode);
	}

	public MypageInvalidPasswordFormatException(
		MypageCode mypageCode,
		String detailMessage
	) {
		super(mypageCode, detailMessage);
	}
}
