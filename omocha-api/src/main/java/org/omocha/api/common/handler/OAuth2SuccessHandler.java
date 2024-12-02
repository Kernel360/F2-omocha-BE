package org.omocha.api.common.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.omocha.api.auth.dto.AuthDto;
import org.omocha.api.auth.jwt.JwtProvider;
import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.domain.common.code.SuccessCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

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

		Map<String, String> resultData = new HashMap<>();
		resultData.put("access_token", result.accessToken());
		resultData.put("refresh_token", result.refreshToken());

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status_code", SuccessCode.MEMBER_LOGIN_SUCCESS.getStatusCode());
		responseBody.put("result_msg", SuccessCode.MEMBER_LOGIN_SUCCESS.getDescription());
		responseBody.put("result_data", resultData);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);

		new ObjectMapper().writeValue(response.getWriter(), responseBody);
	}
}
