package org.auction.client.exception.mypage;

import org.auction.client.common.code.MypageCode;

public class MypageException extends RuntimeException {
	private final MypageCode mypageCode;
	private final String detailMessage;

	public MypageException(
		MypageCode mypageCode
	) {
		super(mypageCode.getResultMsg());
		this.mypageCode = mypageCode;
		this.detailMessage = mypageCode.getResultMsg();
	}

	public MypageException(
		MypageCode mypageCode,
		String detailMessage
	) {
		super(mypageCode.getResultMsg());
		this.mypageCode = mypageCode;
		this.detailMessage = detailMessage;
	}
}
