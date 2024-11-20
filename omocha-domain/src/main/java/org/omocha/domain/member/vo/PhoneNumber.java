package org.omocha.domain.member.vo;

import java.util.regex.Pattern;

import org.omocha.domain.member.exception.InvalidPhoneNumberFormatException;

import com.fasterxml.jackson.annotation.JsonValue;

public record PhoneNumber(String value) {

	private static final String REGEX = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(REGEX);

	public PhoneNumber {
		if (!isValid(value)) {
			throw new InvalidPhoneNumberFormatException(value);
		}
	}

	public static boolean isValid(String value) {
		return PHONE_NUMBER_PATTERN.matcher(value).matches();
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
