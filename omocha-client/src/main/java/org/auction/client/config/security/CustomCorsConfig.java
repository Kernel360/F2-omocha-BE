package org.auction.client.config.security;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomCorsConfig implements CorsConfigurationSource {
	private final String ALLOWED_ORIGIN;
	private final List<String> ALLOWED_METHODS = List.of("POST", "GET", "PATCH", "OPTIONS", "DELETE");

	// yml에 프론트의 주소 작성 후 값 주입
	public CustomCorsConfig(@Value("${url.front}") String BASE_URL) {
		ALLOWED_ORIGIN = BASE_URL;
	}

	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(Collections.singletonList(ALLOWED_ORIGIN));
		config.setAllowedMethods(ALLOWED_METHODS);
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setMaxAge(3600L);

		return config;
	}
}