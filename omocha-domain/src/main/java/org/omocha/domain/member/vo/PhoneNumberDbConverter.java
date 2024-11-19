package org.omocha.domain.member.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneNumberDbConverter implements AttributeConverter<PhoneNumber, String> {
	@Override
	public String convertToDatabaseColumn(PhoneNumber phoneNumber) {
		return phoneNumber != null ? phoneNumber.getValue() : null;
	}

	@Override
	public PhoneNumber convertToEntityAttribute(String phoneNumber) {
		// TODO: VO 객체에 null을 반환?
		return phoneNumber != null ? new PhoneNumber(phoneNumber) : null;
	}
}
