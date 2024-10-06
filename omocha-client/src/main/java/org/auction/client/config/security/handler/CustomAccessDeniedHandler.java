package org.auction.client.config.security.handler;

import java.io.IOException;

import org.auction.client.common.dto.ResultDto;
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
		log.info("[CustomAccessDeniedHandler] :: {}", accessDeniedException.getMessage());
		log.info("[CustomAccessDeniedHandler] :: {}", request.getRequestURL());
		log.info("[CustomAccessDeniedHandler] :: 접근 권한이 없습니다.");

		// TODO: 예외처리 추가 작업 필요
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		ResultDto<Void> resultDto = ResultDto.res(
			HttpServletResponse.SC_FORBIDDEN,
			accessDeniedException.getMessage(),
			null
		);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), resultDto);
	}
}
