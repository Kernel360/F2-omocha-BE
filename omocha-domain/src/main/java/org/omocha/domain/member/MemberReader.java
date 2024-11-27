package org.omocha.domain.member;

import java.util.Optional;

import org.omocha.domain.member.vo.Email;

public interface MemberReader {

	boolean existsByEmail(Email email);

	Member getMember(Long memberId);

	Member getMember(Email email);

	Optional<Member> findMember(MemberCommand.OAuthProvider oAuthProvider);

	boolean existsByEmailAndProviderIsNull(Email email);

	boolean existsByNickname(String nickname);
}
