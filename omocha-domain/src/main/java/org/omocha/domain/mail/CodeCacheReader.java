package org.omocha.domain.mail;

import java.util.Optional;

public interface CodeCacheReader {
	Optional<AuthCode> findCode(String key);

	Optional<Long> findCodeDuration(String key);
}
