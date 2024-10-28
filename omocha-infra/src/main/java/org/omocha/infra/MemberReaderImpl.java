package org.omocha.infra;

import java.util.Optional;

import org.omocha.domain.member.Member;
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

	@Override
	public boolean existsByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	@Override
	public Optional<Member> findByProviderAndProviderId(String provider, String providerId) {
		return memberRepository.findByProviderAndProviderId(provider, providerId);
	}

	@Override
	public boolean existsByEmailAndProviderIsNull(String email) {
		return memberRepository.existsByEmailAndProviderIsNull(email);
	}
}
