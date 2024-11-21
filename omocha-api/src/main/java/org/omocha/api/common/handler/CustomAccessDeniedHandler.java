package org.omocha.api.common.handler;

import java.io.IOException;

import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.common.code.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException
	) throws IOException, ServletException {

		log.info("[CustomAccessDeniedHandler] :: Request URL: {}", request.getRequestURL());
		log.info("[CustomAccessDeniedHandler] :: HTTP Method: {}", request.getMethod());
		log.info("[CustomAccessDeniedHandler] :: Client IP: {}", request.getRemoteAddr());

		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			log.info("[CustomAccessDeniedHandler] :: Authorization Header: {}", authHeader);
		} else {
			log.info("[CustomAccessDeniedHandler] :: Authorization Header is missing");
		}

		log.warn("[CustomAccessDeniedHandler] :: Access denied. Exception type: {}",
			accessDeniedException.getClass().getSimpleName());
		log.warn("[CustomAccessDeniedHandler] :: Exception message: {}", accessDeniedException.getMessage());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		ResultDto<Void> resultDto = ResultDto.res(
			ErrorCode.FORBIDDEN.getStatusCode(),
			ErrorCode.FORBIDDEN.getDescription()
		);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), resultDto);
	}
}