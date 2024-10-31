package org.auction.domain.auction.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AuctionType {
	BASIC("BASIC"),
	LIVE("LIVE");

	private final String value;

	AuctionType(String value) {
		this.value = value;
	}

	@JsonCreator
	public static AuctionType from(String value) {
		for (AuctionType type : AuctionType.values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid AuctionType value: " + value);
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}