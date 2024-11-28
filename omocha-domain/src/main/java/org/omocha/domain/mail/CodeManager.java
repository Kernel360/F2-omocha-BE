package org.omocha.domain.mail;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.omocha.domain.mail.exception.MailCodeKeyNotFoundException;

public class CodeManager {
	private static final long CODE_EXPIRATION_MINUTES = 5; // 유효 기간 상수 (5분)

	protected static Map<String, AuthCode> codes = new ConcurrentHashMap<>();

	public static boolean checkCode(String email, String code) {

		if (codes.get(email) == null) {
			throw new MailCodeKeyNotFoundException(email);
		}

		if (codes.get(email).createdAt.plusMinutes(CODE_EXPIRATION_MINUTES).isBefore(LocalDateTime.now())) {
			return false;
		}
		if (codes.get(email).code.equals(code)) {
			codes.remove(email);
			return true;
		}
		return false;
	}

	public static String addCode(String email) {
		String code = createCode();
		AuthCode authCode = new AuthCode(code);
		codes.put(email, authCode);
		return code;
	}

	public static void removeCode() {
		LocalDateTime now = LocalDateTime.now();

		codes.values().removeIf(value -> value.createdAt.plusMinutes(CODE_EXPIRATION_MINUTES).isBefore(now));

	}

	public static String createCode() {
		return String.valueOf(UUID.randomUUID());
	}
}
