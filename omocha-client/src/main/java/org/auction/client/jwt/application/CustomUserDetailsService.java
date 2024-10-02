package org.auction.client.jwt.application;

import org.auction.client.jwt.UserPrincipal;
import org.auction.client.member.application.MemberService;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(
		String memberIdStr
	) throws UsernameNotFoundException {
		MemberEntity memberEntity = memberService.findMemberByMemberId(Long.valueOf(memberIdStr));

		return new UserPrincipal(memberEntity);
	}
}
