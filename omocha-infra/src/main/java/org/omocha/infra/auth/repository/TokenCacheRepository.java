package org.omocha.infra.auth.repository;

public interface TokenCacheRepository {
	String findValue(String key);

	void storeKey(String key, Long value);

	void deleteKey(String key);
}
