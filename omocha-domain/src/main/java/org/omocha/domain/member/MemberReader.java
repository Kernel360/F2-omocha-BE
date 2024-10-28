package org.omocha.domain.member;

import java.util.Optional;

public interface MemberReader {

	boolean existsByEmail(String email);

	Member findById(Long memberId);

	Member findByEmail(String email);

	Optional<Member> getOptionalMember(MemberCommand.OAuthProvider oAuthProvider);

	boolean existsByEmailAndProviderIsNull(String email);
}
