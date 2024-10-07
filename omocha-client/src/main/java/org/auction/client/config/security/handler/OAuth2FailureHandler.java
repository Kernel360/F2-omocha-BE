package org.auction.client.config.security.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final String REDIRECT_URI;
	private final String ERROR_PARAM_PREFIX = "error";

	// TODO: 실패시 Redirect URI 정해야함 => 아마 home이 아닐까...
	public OAuth2FailureHandler(@Value("${url.base}") String REDIRECT_URI) {
		this.REDIRECT_URI = REDIRECT_URI;
	}

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception
	) throws IOException, ServletException {
		String redirectUrl = UriComponentsBuilder.fromUriString(REDIRECT_URI)
			.queryParam(ERROR_PARAM_PREFIX, exception.getLocalizedMessage())
			.build()
			.toUriString();

		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
