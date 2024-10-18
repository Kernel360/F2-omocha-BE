package org.auction.client.exception.mypage;

import org.auction.client.common.code.MypageCode;

public class MypageNicknameDuplicateException extends MypageException {
	public MypageNicknameDuplicateException(
		MypageCode mypageCode
	) {
		super(mypageCode);
	}

	public MypageNicknameDuplicateException(
		MypageCode mypageCode,
		String detailMessage
	) {
		super(mypageCode, detailMessage);
	}
}
