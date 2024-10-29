package org.omocha.infra;

import static org.omocha.domain.exception.code.MemberCode.*;

import java.util.Optional;

import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberReader;
import org.omocha.infra.repository.MemberRepository;
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
	public boolean existsByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	@Override
	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new RuntimeException(MEMBER_NOT_FOUND.getResultMsg()));
	}

	@Override
	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException(MEMBER_NOT_FOUND.getResultMsg()));
	}

	@Override
	public Optional<Member> getOptionalMember(MemberCommand.OAuthProvider oAuthProvider) {
		return memberRepository.findByProviderAndProviderId(oAuthProvider.provider(), oAuthProvider.providerId());
	}

	@Override
	public boolean existsByEmailAndProviderIsNull(String email) {
		return memberRepository.existsByEmailAndProviderIsNull(email);
	}
}
