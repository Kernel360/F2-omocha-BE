package org.auction.client.config.security;

import org.auction.client.config.security.filter.JwtAuthFilter;
import org.auction.client.config.security.handler.CustomAccessDeniedHandler;
import org.auction.client.config.security.handler.CustomAuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomCorsConfig customCorsConfig;
	private final JwtAuthFilter jwtAuthFilter;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
	public static final String[] PERMITTED_URI = {"/api/v1/auth/**", "/swagger-ui/**"};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(corsCustomizer -> corsCustomizer.configurationSource(customCorsConfig))
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(PERMITTED_URI).permitAll()
				.anyRequest().authenticated()
			)

			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling(conf -> conf
				.accessDeniedHandler(customAccessDeniedHandler)
				.authenticationEntryPoint(customAuthenticationEntryPointHandler)
			);

		return http.build();
	}
}
