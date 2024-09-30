package org.auction.client.config.security.filter;

import static org.auction.client.config.security.SecurityConfig.*;

import java.io.IOException;
import java.util.Arrays;

import org.auction.client.jwt.JwtCategory;
import org.auction.client.jwt.application.JwtService;
import org.auction.client.member.application.MemberService;
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
	private final MemberService memberService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		if (isPermittedURI(request.getRequestURI())) {
			SecurityContextHolder.getContext().setAuthentication(null);
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = jwtService.resolveTokenFromCookie(request, JwtCategory.ACCESS);
		if (jwtService.validateAccessToken(accessToken)) {
			setAuthenticationToContext(accessToken);
			filterChain.doFilter(request, response);
			return;
		}

		String refreshToken = jwtService.resolveTokenFromCookie(request, JwtCategory.REFRESH);
		MemberEntity memberEntity = jwtService.findMemberByRefreshToken(refreshToken);
		if (jwtService.validateRefreshToken(refreshToken)) {
			String reissuedAccessToken = jwtService.generateAccessToken(response, memberEntity);
			jwtService.generateRefreshToken(response, memberEntity);

			setAuthenticationToContext(reissuedAccessToken);
			filterChain.doFilter(request, response);
			return;
		}

		jwtService.logout(memberEntity, response);
	}

	private boolean isPermittedURI(String requestURI) {
		return Arrays.stream(PERMITTED_URI)
			.anyMatch(permitted -> {
				String replace = permitted.replace("*", "");
				return requestURI.contains(replace) || replace.contains(requestURI);
			});
	}

	private void setAuthenticationToContext(String accessToken) {
		Authentication authentication = jwtService.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
