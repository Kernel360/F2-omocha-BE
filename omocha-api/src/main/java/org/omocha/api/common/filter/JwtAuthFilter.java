package org.omocha.api.common.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.omocha.api.common.auth.jwt.JwtCategory;
import org.omocha.api.common.auth.jwt.JwtProvider;
import org.omocha.api.common.auth.jwt.UserPrincipal;
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

		// TODO: 추후 리팩토링 필요함
		if (StringUtils.isBlank(accessToken)) {
			skipThisFilter(request, response, filterChain);
			return;
		}

		// TODO: userPrincipal에서 memberId 가져와서 비교한 후 진행하도록
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
		setAuthenticationToContext(accessToken);
		filterChain.doFilter(request, response);
	}

	private void refreshTokenHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String refreshToken = jwtProvider.resolveTokenFromCookie(request, JwtCategory.REFRESH);
		Long memberId = jwtProvider.getMemberIdFromToken(refreshToken);

		if (jwtProvider.validateRefreshToken(refreshToken)) {
			String reissuedAccessToken = jwtProvider.generateAccessToken(memberId, response);
			jwtProvider.generateRefreshToken(memberId, response);

			setAuthenticationToContext(reissuedAccessToken);
		} else {
			jwtProvider.logout(memberId, response);
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthenticationToContext(String accessToken) {
		Authentication authentication = getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private Authentication getAuthentication(String accessToken) {
		Long memberId = jwtProvider.getMemberIdFromToken(accessToken);
		Member member = memberReader.findById(memberId);
		UserDetails principal = new UserPrincipal(member);

		return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
	}
}
