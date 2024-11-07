package org.omocha.domain.auction.review;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RatingDbConverter implements AttributeConverter<Rating, Double> {
	@Override
	public Double convertToDatabaseColumn(Rating rating) {
		return 0.0;
	}

	@Override
	public Rating convertToEntityAttribute(Double aDouble) {
		return new Rating(aDouble);
	}
}