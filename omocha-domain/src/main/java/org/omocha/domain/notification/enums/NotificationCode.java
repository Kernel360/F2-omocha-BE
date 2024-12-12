package org.omocha.domain.notification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationCode {
	BID_SELLER("판매자 새 입찰 알림"),
	BID_BUYER("구매자 새 입찰 알림"),
	CONCLUDE_SELLER("판매자 낙찰 알림"),
	CONCLUDE_BUYER("구매자 낙찰 알림"),
	CONCLUDE_OTHER_BUYER("구매자 패찰 알림"),
	CONCLUDE_NO_BIDS("판매자 입찰 없이 종료 알림");

	private final String description;
}
