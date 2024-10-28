package org.omocha.domain.member;

public interface MemberReader {

	boolean existsByEmail(String email);

	Member findById(Long memberId);

	Member findByEmail(String email);

	Member findByProviderAndProviderId(String provider, String providerId);

	boolean existsByEmailAndProviderIsNull(String email);
}
