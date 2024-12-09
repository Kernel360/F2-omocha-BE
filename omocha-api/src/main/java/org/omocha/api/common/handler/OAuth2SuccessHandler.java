package org.omocha.api.common.handler;

import java.io.IOException;

import org.omocha.api.auth.dto.AuthDto;
import org.omocha.api.auth.jwt.JwtProvider;
import org.omocha.api.auth.jwt.UserPrincipal;
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

	private final JwtProvider jwtProvider;

	@Value("${url.oauth-callback}")
	private String CALLBACK_URI;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {
		log.info("OAuth2SuccessHandler onAuthenticationSuccess");

		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

		AuthDto.JwtResponse result = jwtProvider.generateToken(userPrincipal.getId());

		log.info("MemberId: {}, AccessToken: {}, RefreshToken: {}",
			userPrincipal.getId(), result.accessToken(), result.refreshToken());

		String redirectUrl =
			CALLBACK_URI + "?provider=" + userPrincipal.getProvider()
				+ "&access_token=" + result.accessToken() + "&refresh_token=" + result.refreshToken();

		clearAuthenticationAttributes(request);
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
