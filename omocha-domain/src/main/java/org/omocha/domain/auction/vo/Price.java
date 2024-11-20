package org.omocha.domain.auction.vo;

import org.omocha.domain.auction.exception.NegativePriceException;
import org.omocha.domain.auction.exception.PriceTooHighException;

import com.fasterxml.jackson.annotation.JsonValue;

public record Price(long value) {

	public static final int MIN = 0;
	public static final int MAX = 1_000_000_000;

	public Price {
		if (value < MIN) {
			throw new NegativePriceException(value);
		}
		if (value > MAX) {
			throw new PriceTooHighException(value);
		}
	}

	@JsonValue
	public long getValue() {
		return value;
	}
}