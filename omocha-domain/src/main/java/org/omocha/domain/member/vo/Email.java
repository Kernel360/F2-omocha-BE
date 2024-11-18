package org.omocha.domain.member.vo;

import java.util.regex.Pattern;

import org.omocha.domain.member.exception.InvalidEmailException;

import com.fasterxml.jackson.annotation.JsonValue;

public record Email(String email) {

	private static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX);

	public Email {
		if (!isValid(email)) {
			throw new InvalidEmailException(email);
		}
	}

	public static boolean isValid(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}

	@JsonValue
	public String getValue() {
		return email;
	}

	@Override
	public String toString() {
		return email;
	}
}