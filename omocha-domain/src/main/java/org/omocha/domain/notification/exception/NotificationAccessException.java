package org.omocha.domain.notification.exception;

import org.omocha.domain.common.code.ErrorCode;

public class NotificationAccessException extends NotificationException {
	public NotificationAccessException(Long readMemberId, Long notifyMemberId) {
		super(
			ErrorCode.AUCTION_NOT_FOUND,
			"알림에 접근하려는 사용자가 알림 소유자와 일치하지 않습니다. readMemberId = " + readMemberId + ", notifyMemberId = " + notifyMemberId
		);
	}
}
