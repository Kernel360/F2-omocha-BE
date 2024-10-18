package org.auction.client.config.security.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.auction.client.jwt.JwtCategory;
import org.auction.client.jwt.application.JwtService;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		String accessToken = jwtService.resolveTokenFromCookie(request, JwtCategory.ACCESS);

		// TODO: 추후 리팩토링 필요함
		if (StringUtils.isBlank(accessToken)) {
			skipThisFilter(request, response, filterChain);
			return;
		}

		// TODO: userPrincipal에서 memberId 가져와서 비교한 후 진행하도록
		if (jwtService.validateAccessToken(accessToken)) {
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
		String refreshToken = jwtService.resolveTokenFromCookie(request, JwtCategory.REFRESH);
		MemberEntity memberEntity = jwtService.findMemberByRefreshToken(refreshToken);

		if (jwtService.validateRefreshToken(refreshToken)) {
			String reissuedAccessToken = jwtService.generateAccessToken(response, memberEntity);
			jwtService.generateRefreshToken(response, memberEntity);

			setAuthenticationToContext(reissuedAccessToken);
		} else {
			jwtService.logout(memberEntity, response);
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthenticationToContext(
		String accessToken
	) {
		Authentication authentication = jwtService.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
