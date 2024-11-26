package org.omocha.api.common.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomCorsConfig implements CorsConfigurationSource {

	@Value("${url.client-domain}")
	private String Client;
	@Value("${url.server-domain}")
	private String Server;

	private List<String> ALLOWED_ORIGIN;

	@PostConstruct
	public void init() {
		ALLOWED_ORIGIN = List.of(Client, Server);
	}

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