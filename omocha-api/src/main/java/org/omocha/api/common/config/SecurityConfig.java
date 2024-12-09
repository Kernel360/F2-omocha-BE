package org.omocha.api.common.config;

import org.omocha.api.auth.oauth.CustomOAuth2UserService;
import org.omocha.api.common.filter.JwtAuthFilter;
import org.omocha.api.common.handler.CustomAccessDeniedHandler;
import org.omocha.api.common.handler.CustomAuthenticationEntryPointHandler;
import org.omocha.api.common.handler.OAuth2FailureHandler;
import org.omocha.api.common.handler.OAuth2SuccessHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
	private final OAuth2SuccessHandler successHandler;
	private final OAuth2FailureHandler failureHandler;

	public static final String[] PERMITTED_ALL_URI = {
		"/health",                                                           // AWS ELB health check 경로
		"/swagger-ui/**", "/v3/api-docs/**",                                 // Swagger 관련 경로
		"/sub/**", "/pub/**", "/{roomId}/messages", "/omocha-websocket/**",    // websoket 경로
		"/api/v2/auth/**", "/api/v2/mail/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(corsCustomizer -> corsCustomizer.configurationSource(customCorsConfig))
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(PERMITTED_ALL_URI).permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v2/auctions/likes").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/v2/auctions/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v2/member/{member_id}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v2/bids/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v2/questions/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v2/reviews/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v2/categories/**").permitAll()
				.anyRequest().authenticated())

			.oauth2Login(
				oauth -> oauth.authorizationEndpoint(authorization -> authorization.baseUri("/api/v2/oauth/authorize"))
					.redirectionEndpoint(redirection -> redirection.baseUri("/api/v2/login/oauth2/code/*"))
					.userInfoEndpoint(userinfo -> userinfo.userService(customOAuth2UserService))
					.successHandler(successHandler)
					.failureHandler(failureHandler))

			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling(conf -> conf.accessDeniedHandler(customAccessDeniedHandler)
				.authenticationEntryPoint(customAuthenticationEntryPointHandler));

		return http.build();
	}

	// 스프링 시큐리티 기능 비활성화 (정적 자원 시큐리티 ignore)
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
}