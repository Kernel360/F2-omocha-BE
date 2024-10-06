package org.auction.client.config.security.handler;

import java.io.IOException;

import org.auction.client.common.dto.ResultDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) throws IOException, ServletException {
		log.info("[CustomAuthenticationEntryPointHandler] :: {}", authException.getMessage());
		log.info("[CustomAuthenticationEntryPointHandler] :: {}", request.getRequestURL());
		log.info("[CustomAuthenticationEntryPointHandler] :: 올바르지 않은 토큰입니다.");

		// TODO: 예외처리 추가 작업 필요
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ResultDto<Void> resultDto = ResultDto.res(
			HttpServletResponse.SC_UNAUTHORIZED,
			authException.getMessage(),
			null
		);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), resultDto);
	}
}