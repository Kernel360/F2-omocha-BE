package org.omocha.infra.auth;

public interface TokenCacheReader {
	String findValue(String key);
}
