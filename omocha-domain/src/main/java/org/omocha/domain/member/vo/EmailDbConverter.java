package org.omocha.domain.member.vo;

import jakarta.persistence.AttributeConverter;

public class EmailDbConverter implements AttributeConverter<Email, String> {
	@Override
	public String convertToDatabaseColumn(Email email) {
		return email.getValue();
	}

	@Override
	public Email convertToEntityAttribute(String email) {
		return new Email(email);
	}
}
