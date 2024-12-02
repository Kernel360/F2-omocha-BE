package org.omocha.api.common.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.omocha.domain.common.code.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception
	) throws IOException, ServletException {
		log.warn("Client IP: {}, Exception type: {}, Exception message: {}",
			exception.getClass().getSimpleName(),
			exception.getMessage(),
			request.getRemoteAddr()
		);

		//TODO: 추후 result_date 삭제
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status_code", ErrorCode.OAUTH_FAILURE.getStatusCode());
		responseBody.put("result_msg", ErrorCode.OAUTH_FAILURE.getDescription());
		responseBody.put("result_data", null);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		new ObjectMapper().writeValue(response.getWriter(), responseBody);
	}
}
