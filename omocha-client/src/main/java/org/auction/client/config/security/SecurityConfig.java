package org.auction.client.config.security;

import org.auction.client.config.security.filter.JwtAuthFilter;
import org.auction.client.config.security.handler.CustomAccessDeniedHandler;
import org.auction.client.config.security.handler.CustomAuthenticationEntryPointHandler;
import org.auction.client.config.security.handler.OAuth2FailureHandler;
import org.auction.client.config.security.handler.OAuth2SuccessHandler;
import org.auction.client.oauth.application.CustomOAuth2UserService;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
		"/swagger-ui/**",
		"/v3/api-docs/**", // Swagger 관련 경로
		"/api/v1/auth/**"
	};

	// 스프링 시큐리티 기능 비활성화
	//TODO: 정적 자원 접근에러 파악해야함
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
			.requestMatchers(
				new AntPathRequestMatcher("/img/**"),
				new AntPathRequestMatcher("/css/**"),
				new AntPathRequestMatcher("/js/**"),
				new AntPathRequestMatcher("/favicon.ico")
			);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(corsCustomizer -> corsCustomizer.configurationSource(customCorsConfig))
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/favicon.ico").denyAll()    // favicon.ico 임시용
				.requestMatchers(PERMITTED_ALL_URI).permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/auction/*").permitAll()
				.anyRequest().authenticated()
			)

			.oauth2Login(oauth -> oauth
				.authorizationEndpoint(authorization -> authorization
					.baseUri("/api/v1/oauth/authorize"))
				.redirectionEndpoint(redirection -> redirection
					.baseUri("/api/v1/login/oauth2/code/*"))
				.userInfoEndpoint(userinfo -> userinfo.userService(customOAuth2UserService))
				.successHandler(successHandler)
				.failureHandler(failureHandler)
			)

			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling(conf -> conf
				.accessDeniedHandler(customAccessDeniedHandler)
				.authenticationEntryPoint(customAuthenticationEntryPointHandler)
			);

		return http.build();
	}
}
