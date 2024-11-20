package org.omocha.domain.review.rating;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RatingDbConverter implements AttributeConverter<Rating, Double> {
	@Override
	public Double convertToDatabaseColumn(Rating rating) {
		return rating != null ? rating.getValue() : 0;
	}

	@Override
	public Rating convertToEntityAttribute(Double rating) {
		return new Rating(rating);
	}
}