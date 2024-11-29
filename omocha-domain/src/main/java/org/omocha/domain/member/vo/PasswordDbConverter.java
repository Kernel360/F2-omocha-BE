package org.omocha.domain.member.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PasswordDbConverter implements AttributeConverter<Password, String> {
	@Override
	public String convertToDatabaseColumn(Password password) {
		return password != null ? password.getValue() : null;
	}

	@Override
	public Password convertToEntityAttribute(String password) {
		// TODO: VO 객체에 null을 반환?
		return password != null ? new Password(password) : null;
	}
}
