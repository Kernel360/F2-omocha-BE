package org.omocha.domain.member;

import java.util.Optional;

public interface MemberReader {

	boolean existsByEmail(String email);

	Optional<Member> findByEmail(String email);

	Optional<Member> findByProviderAndProviderId(String provider, String providerId);

	boolean existsByEmailAndProviderIsNull(String email);
}
