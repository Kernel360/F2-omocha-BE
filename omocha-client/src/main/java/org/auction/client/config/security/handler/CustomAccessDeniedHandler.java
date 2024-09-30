package org.auction.client.config.security.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

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
		log.info("[CustomAccessDeniedHandler] :: 토근 정보가 만료되었거나 존재하지 않음");

		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}
