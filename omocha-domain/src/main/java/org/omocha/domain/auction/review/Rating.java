package org.omocha.domain.auction.review;

import org.omocha.domain.exception.InvalidRatingException;

import com.fasterxml.jackson.annotation.JsonValue;

public record Rating(Double value) {

	public static final int MIN = 0;
	public static final int MAX = 5;

	public Rating {
		if (!isValidRating(value)) {
			throw new InvalidRatingException(value);
		}
	}

	public static boolean isValidRating(Double value) {
		return value >= MIN && value <= MAX;
	}

	@JsonValue
	public Double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
