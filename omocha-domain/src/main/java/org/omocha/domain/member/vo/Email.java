package org.omocha.domain.member.vo;

import java.util.regex.Pattern;

import org.omocha.domain.member.exception.InvalidEmailFormatException;

import com.fasterxml.jackson.annotation.JsonValue;

public record Email(String value) {

	private static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX);

	public Email {
		if (!EMAIL_PATTERN.matcher(value).matches()) {
			throw new InvalidEmailFormatException(value);
		}
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}