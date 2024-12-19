package org.omocha.infra.member;

import java.util.Optional;

import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberReader;
import org.omocha.domain.member.exception.MemberNotFoundExceptionForEmail;
import org.omocha.domain.member.exception.MemberNotFoundExceptionForId;
import org.omocha.domain.member.vo.Email;
import org.omocha.infra.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

	private final MemberRepository memberRepository;

	// TODO : Exception 관련 수정 필요

	@Override
	public boolean existsByEmail(Email email) {
		return memberRepository.existsByEmail(email);
	}

	@Override
	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundExceptionForId(memberId));
	}

	@Override
	public Member getMember(Email email) {
		return memberRepository.findByEmailAndProviderIsNull(email)
			.orElseThrow(() -> new MemberNotFoundExceptionForEmail(email));
	}

	@Override
	public Optional<Member> findMember(MemberCommand.OAuthProvider oAuthProvider) {
		return memberRepository.findByProviderAndProviderId(oAuthProvider.provider(), oAuthProvider.providerId());
	}

	@Override
	public boolean existsByEmailAndProviderIsNull(Email email) {
		return memberRepository.existsByEmailAndProviderIsNull(email);
	}

	@Override
	public boolean existsByNickname(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}
}
