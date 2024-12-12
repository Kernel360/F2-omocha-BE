package org.omocha.domain.notification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventName {
	CONNECT("SSE 연결"),
	BID("입찰 알림"),
	CONCLUDE("낙찰 알림");

	private final String description;
}
