package org.auction.domain.auction.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AuctionStatus {
	PREBID("PREBID"),
	BIDDING("BIDDING"),
	CONCLUDED("CONCLUDED"),
	COMPLETED("COMPLETED");

	private final String value;

	AuctionStatus(String value) {
		this.value = value;
	}

	@JsonCreator
	public static AuctionStatus from(String value) {
		for (AuctionStatus status : AuctionStatus.values()) {
			if (status.getValue().equalsIgnoreCase(value)) { // 대소문자 구분 없이 비교
				return status;
			}
		}
		// TODO : return exception 처리 해야함
		return null; // 값이 맞지 않을 때 처리
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}