package org.omocha.api.common.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final String ERROR_PARAM_PREFIX = "error";

	@Value("${url.base}")
	private String REDIRECT_URI;

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception
	) throws IOException, ServletException {
		log.info("OAuth2FailureHandler onAuthenticationFailure");

		String redirectUrl = UriComponentsBuilder.fromUriString(REDIRECT_URI)
			.queryParam(ERROR_PARAM_PREFIX, exception.getLocalizedMessage())
			.build()
			.toUriString();

		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
