package org.omocha.domain.member.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailDbConverter implements AttributeConverter<Email, String> {
	@Override
	public String convertToDatabaseColumn(Email email) {
		return email != null ? email.getValue() : null;
	}

	@Override
	public Email convertToEntityAttribute(String email) {
		// TODO: VO 객체에 null을 반환?
		return email != null ? new Email(email) : null;
	}
}
