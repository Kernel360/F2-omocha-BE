package org.omocha.infra.repository;

import java.util.Optional;

import org.omocha.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(String email);

	Optional<Member> findByEmail(String email);

	Optional<Member> findByProviderAndProviderId(String provider, String providerId);

	boolean existsByEmailAndProviderIsNull(String email);

	boolean existsByNickname(String nickName);
}
