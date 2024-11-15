package org.omocha.infra.member;

import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberStore;
import org.omocha.infra.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

	private final MemberRepository memberRepository;

	@Override
	public Member addMember(Member member) {
		return memberRepository.save(member);
	}
}
