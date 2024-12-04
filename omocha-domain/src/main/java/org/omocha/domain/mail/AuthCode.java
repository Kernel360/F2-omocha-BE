package org.omocha.domain.mail;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthCode {
	@JsonProperty
	String code;
	@JsonProperty
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	LocalDateTime createdAt;

	public AuthCode(String code) {
		this.code = code;
		this.createdAt = LocalDateTime.now();
	}
}
