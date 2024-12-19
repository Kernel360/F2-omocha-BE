package org.omocha.infra.member.repository;

import java.util.Optional;

import org.omocha.domain.member.Member;
import org.omocha.domain.member.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(Email email);

	Optional<Member> findByEmailAndProviderIsNull(Email email);

	Optional<Member> findByProviderAndProviderId(String provider, String providerId);

	boolean existsByEmailAndProviderIsNull(Email email);

	boolean existsByNickname(String nickname);
}
