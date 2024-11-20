package org.omocha.api.common.util;

import org.omocha.domain.member.exception.MemberIdenticalPassword;
import org.omocha.domain.member.exception.MemberInvalidPasswordException;
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

	public void match(String enteredPassword, String storedPassword, Long memberId) {

		// TODO : Exception 변경해야함
		if (!passwordEncoder.matches(enteredPassword, storedPassword)) {
			throw new MemberInvalidPasswordException(memberId);
		}

	}

	public void validateIdenticalPassword(String currentPassword, String newPassword, Long memberId) {
		if (currentPassword.equals(newPassword)) {
			throw new MemberIdenticalPassword(memberId);
		}
	}

}
