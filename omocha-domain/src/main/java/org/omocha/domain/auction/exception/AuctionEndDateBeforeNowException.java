package org.omocha.domain.auction.exception;

import java.time.LocalDateTime;

import org.omocha.domain.common.code.ErrorCode;

public class AuctionEndDateBeforeNowException extends AuctionException {
	public AuctionEndDateBeforeNowException(LocalDateTime endDate, LocalDateTime nowDate) {
		super(
			ErrorCode.END_TIME_BEFORE_NOW,
			"종료 시간이 현재 시간보다 이전입니다. endDate= " + endDate + "nowDate" + nowDate
		);
	}
}
