package org.omocha.api.auth.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.omocha.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {

	private final Member member;
	private Map<String, Object> attributes;

	public UserPrincipal(Member member) {
		this.member = member;
	}

	public UserPrincipal(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
	}

	// TODO : google은 nickname을 반환안하고, naver는 nickname을 반환함. 추후 고민해야함
	@Override
	public String getName() {
		return member.getNickname();
	}

	public Long getId() {
		return member.getMemberId();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public String getPassword() {
		return member.getPassword().getValue();
	}

	public String getProvider() {
		return member.getProvider();
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
