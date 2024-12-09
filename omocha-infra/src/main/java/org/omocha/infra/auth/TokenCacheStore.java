package org.omocha.infra.auth;

public interface TokenCacheStore {
	void storeKey(String key, Long value);

	void deleteKey(String key);
}
