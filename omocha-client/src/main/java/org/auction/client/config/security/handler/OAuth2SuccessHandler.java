package org.auction.client.config.security.handler;

import java.io.IOException;

import org.auction.client.jwt.UserPrincipal;
import org.auction.client.jwt.application.JwtService;
import org.auction.domain.member.infrastructure.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

		jwtService.generateAccessToken(response, userPrincipal.getMemberEntity());
		jwtService.generateRefreshToken(response, userPrincipal.getMemberEntity());
	}
}
