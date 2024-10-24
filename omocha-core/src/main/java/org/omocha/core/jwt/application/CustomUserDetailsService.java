package org.omocha.core.jwt.application;

import org.omocha.core.jwt.UserPrincipal;
import org.omocha.client.member.application.MemberService;
import org.omocha.domain.member.MemberEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberService memberService;

	@Override
	public UserPrincipal loadUserByUsername(
		String memberIdStr
	) throws UsernameNotFoundException {
		MemberEntity memberEntity = memberService.findMember(Long.valueOf(memberIdStr));

		return new UserPrincipal(memberEntity);
	}
}
