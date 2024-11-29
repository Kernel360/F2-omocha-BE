package org.omocha.api.common.util;

import org.omocha.domain.member.exception.MemberIdenticalPassword;
import org.omocha.domain.member.exception.MemberInvalidPasswordException;
import org.omocha.domain.member.vo.Password;
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

	public void match(String enteredPassword, Password storedEncryptedPassword, Long memberId) {
		if (!passwordEncoder.matches(enteredPassword, storedEncryptedPassword.getValue())) {
			throw new MemberInvalidPasswordException(memberId);
		}
	}

	public void validateIdenticalPassword(String currentPassword, Password newEncryptedPassword, Long memberId) {
		if (currentPassword.equals(newEncryptedPassword.getValue())) {
			throw new MemberIdenticalPassword(memberId);
		}
	}

}
