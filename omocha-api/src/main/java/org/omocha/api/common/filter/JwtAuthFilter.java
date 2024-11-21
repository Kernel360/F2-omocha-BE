package org.omocha.api.common.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.omocha.api.auth.jwt.JwtCategory;
import org.omocha.api.auth.jwt.JwtProvider;
import org.omocha.api.auth.jwt.RefreshTokenManager;
import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final MemberReader memberReader;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		String accessToken = jwtProvider.resolveTokenFromCookie(request, JwtCategory.ACCESS);

		if (StringUtils.isBlank(accessToken)) {
			skipThisFilter(request, response, filterChain);
			return;
		}

		if (jwtProvider.validateAccessToken(accessToken)) {
			passThisFilter(request, response, filterChain, accessToken);
			return;
		}

		refreshTokenHandle(request, response, filterChain);
	}

	private void skipThisFilter(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(null);
		filterChain.doFilter(request, response);
	}

	private void passThisFilter(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain,
		String accessToken
	) throws IOException, ServletException {
		Long memberId = jwtProvider.getMemberIdFromToken(accessToken);
		setAuthenticationToContext(memberId);
		filterChain.doFilter(request, response);
	}

	private void refreshTokenHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws IOException, ServletException {
		String refreshToken = jwtProvider.resolveTokenFromCookie(request, JwtCategory.REFRESH);
		Long memberId = RefreshTokenManager.findMemberIdByRefreshToken(refreshToken);

		if (memberId != null && jwtProvider.validateRefreshToken(refreshToken)) {
			jwtProvider.generateAccessToken(memberId, response);
			jwtProvider.generateRefreshToken(memberId, response);
			setAuthenticationToContext(memberId);
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthenticationToContext(Long memberId) {
		Member member = memberReader.getMember(memberId);
		UserDetails principal = new UserPrincipal(member);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
			principal, "", principal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
