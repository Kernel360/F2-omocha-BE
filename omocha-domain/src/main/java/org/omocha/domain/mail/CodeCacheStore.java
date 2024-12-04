package org.omocha.domain.mail;

public interface CodeCacheStore {

	void storeCode(String email, AuthCode code);

	void deleteCode(String email);
}
