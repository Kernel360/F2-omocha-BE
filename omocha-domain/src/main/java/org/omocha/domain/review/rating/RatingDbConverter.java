package org.omocha.domain.review.rating;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RatingDbConverter implements AttributeConverter<Rating, Double> {
	@Override
	public Double convertToDatabaseColumn(Rating rating) {
		return rating.getValue();
	}

	@Override
	public Rating convertToEntityAttribute(Double aDouble) {
		return new Rating(aDouble);
	}
}