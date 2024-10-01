package org.auction.client.jwt;

import java.util.Collection;
import java.util.Collections;

import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails {

	private final MemberEntity memberEntity;

	public UserPrincipal(MemberEntity memberEntity) {
		this.memberEntity = memberEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(memberEntity.getRole().name()));
	}

	@Override
	public String getUsername() {
		return String.valueOf(memberEntity.getMemberId());
	}

	@Override
	public String getPassword() {
		return memberEntity.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
