package org.auction.client.config.security.handler;

import java.io.IOException;

import org.auction.client.jwt.UserPrincipal;
import org.auction.client.jwt.application.JwtService;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${url.base}")
	private String REDIRECT_URI;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {

		// TODO: 로그인 페이지에서 로그인요청을 했다면 로그인 페이지로
		//       다른 페이지에서 권한이 필요해 로그인 요청을 했다면 해당 페이지로 redirect 로직 필요
		log.info("OAuth2SuccessHandler onAuthenticationSuccess");

		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

		jwtService.generateAccessToken(response, userPrincipal.getMemberEntity());
		jwtService.generateRefreshToken(response, userPrincipal.getMemberEntity());

		clearAuthenticationAttributes(request);

		getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI);
	}
}
