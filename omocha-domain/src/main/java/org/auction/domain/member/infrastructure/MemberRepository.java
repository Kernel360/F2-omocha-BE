package org.auction.domain.member.infrastructure;

import java.util.Optional;

import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	boolean existsByLoginId(String loginId);

	Optional<MemberEntity> findByLoginId(String loginId);

	Optional<MemberEntity> findByProviderAndProviderId(String provider, String providerId);
}
