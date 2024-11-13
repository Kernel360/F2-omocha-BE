package org.omocha.domain.member;

import java.util.Optional;

public interface MemberReader {

	boolean existsByEmail(String email);

	Member getMember(Long memberId);

	Member getMember(String email);

	Optional<Member> findMember(MemberCommand.OAuthProvider oAuthProvider);

	boolean existsByEmailAndProviderIsNull(String email);

	boolean existsByNickName(String nickName);
}
