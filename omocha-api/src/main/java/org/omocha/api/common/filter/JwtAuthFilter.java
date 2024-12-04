package org.omocha.api.common.filter;

import static org.omocha.api.common.config.SecurityConfig.*;

import java.io.IOException;
import java.util.Arrays;

import org.omocha.api.auth.jwt.JwtProvider;
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

		if (isPermittedUri(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = request.getHeader("Authorization");
		if (jwtProvider.validateAccessToken(accessToken)) {
			setAuthenticationToContext(accessToken);
		}

		filterChain.doFilter(request, response);
	}

	private boolean isPermittedUri(String requestUri) {
		return Arrays.stream(PERMITTED_ALL_URI)
			.anyMatch(permitted -> {
				String replace = permitted.replace("*", "");
				return requestUri.contains(replace) || replace.contains(requestUri);
			});
	}

	private void setAuthenticationToContext(String accessToken) {
		Long memberId = jwtProvider.getMemberIdFromToken(accessToken);

		Member member = memberReader.getMember(memberId);
		UserDetails principal = new UserPrincipal(member);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
			principal, "", principal.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
