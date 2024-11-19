package org.omocha.domain.auction.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PriceDbConverter implements AttributeConverter<Price, Long> {

	@Override
	public Long convertToDatabaseColumn(Price price) {
		return price.getValue();
	}

	@Override
	public Price convertToEntityAttribute(Long price) {
		if (price == null) {
			return new Price(0L);
		}

		return new Price(price);
	}
}
