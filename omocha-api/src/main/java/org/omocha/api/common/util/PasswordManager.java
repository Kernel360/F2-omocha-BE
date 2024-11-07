package org.omocha.api.common.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordManager {

	private final PasswordEncoder passwordEncoder;

	public String encrypt(String password) {
		return passwordEncoder.encode(password);
	}

	public void match(String oldPassword, String newPassword) {

		// TODO : Exception 변경해야함
		if (!passwordEncoder.matches(oldPassword, newPassword)) {
			throw new RuntimeException("비밀번호가 맞지 않음");
		}

	}

}
