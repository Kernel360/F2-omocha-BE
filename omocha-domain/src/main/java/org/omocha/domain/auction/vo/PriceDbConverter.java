package org.omocha.domain.auction.vo;

import jakarta.persistence.AttributeConverter;

public class PriceDbConverter implements AttributeConverter<Price, Long> {

	@Override
	public Long convertToDatabaseColumn(Price price) {
		return price.getValue();
	}

	@Override
	public Price convertToEntityAttribute(Long value) {
		if (value != null) {
			return new Price(value);
		}

		return new Price(0L);
	}
}
