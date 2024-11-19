package org.omocha.domain.auction.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PriceDbConverter implements AttributeConverter<Price, Long> {

	@Override
	public Long convertToDatabaseColumn(Price price) {
		return price != null ? price.getValue() : null;
	}

	@Override
	public Price convertToEntityAttribute(Long price) {
		// TODO: VO 객체에 null을 반환?
		return price != null ? new Price(price) : null;
	}
}
