package org.omocha.domain.mail;

import java.time.LocalDateTime;
import java.util.UUID;

import org.omocha.domain.mail.exception.MailAuthCodeMisMachException;
import org.omocha.domain.mail.exception.MailCodeKeyNotFoundException;
import org.omocha.domain.mail.exception.MailRetryExcessException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CodeManager {

	private final CodeCacheStore codeCacheStore;
	private final CodeCacheReader codeCacheReader;

	public static long CODE_EXPIRATION_MINUTES = 5;

	public boolean checkCode(String email, String requestCode) {

		AuthCode savedCode = codeCacheReader.findCode(email)
			.orElseThrow(() -> new MailCodeKeyNotFoundException(email, requestCode));

		if (!requestCode.equals(savedCode.code)) {
			throw new MailAuthCodeMisMachException(email, requestCode, savedCode.code);
		}

		if (savedCode.createdAt.plusMinutes(CODE_EXPIRATION_MINUTES).isBefore(LocalDateTime.now())) {
			return false;
		}

		return true;
	}

	public String addCode(String email) {
		Long codeDuration = codeCacheReader.findCodeDuration(email).orElseGet(() -> {
			return (Long)0L;
		});

		if (codeDuration > 1785) {
			throw new MailRetryExcessException();
		}

		String code = createCode();
		AuthCode authCode = new AuthCode(code);
		codeCacheStore.storeCode(email, authCode);

		return code;
	}

	public String createCode() {
		return String.valueOf(UUID.randomUUID());
	}
}
