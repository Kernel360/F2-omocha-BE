package org.auction.client.exception.mypage;

import org.auction.client.common.code.MypageCode;

public class MypageInvalidProfileImageException extends MypageException {
	public MypageInvalidProfileImageException(
		MypageCode mypageCode
	) {
		super(mypageCode);
	}

	public MypageInvalidProfileImageException(
		MypageCode mypageCode,
		String detailMessage
	) {
		super(mypageCode, detailMessage);
	}
}
