package org.auction.client.config.security;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomCorsConfig implements CorsConfigurationSource {
	private final List<String> ALLOWED_ORIGIN = List.of(
		"https://api.omocha-auction.com",
		"https://www.omocha-auction.com",
		"https://local.omocha-auction.com",
		"http://localhost:3000",
		"http://localhost:3001"
	);
	private final List<String> ALLOWED_METHODS = List.of("POST", "GET", "PATCH", "OPTIONS", "DELETE");

	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(ALLOWED_ORIGIN);
		config.setAllowedMethods(ALLOWED_METHODS);
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setMaxAge(3600L);

		return config;
	}
}