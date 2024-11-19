package org.omocha.domain.review.rating;

import org.omocha.domain.review.exception.InvalidRatingException;

import com.fasterxml.jackson.annotation.JsonValue;

public record Rating(double value) {

	public static final int MIN = 0;
	public static final int MAX = 5;

	public Rating {
		if (!isValidRating(value)) {
			throw new InvalidRatingException(value);
		}
	}

	public static boolean isValidRating(double value) {
		return value >= MIN && value <= MAX;
	}

	@JsonValue
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
