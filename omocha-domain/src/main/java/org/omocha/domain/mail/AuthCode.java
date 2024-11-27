package org.omocha.domain.mail;

import java.time.LocalDateTime;

public class AuthCode {
	String code;
	LocalDateTime createdAt;

	public AuthCode(String code) {
		this.code = code;
		this.createdAt = LocalDateTime.now();
	}
}
