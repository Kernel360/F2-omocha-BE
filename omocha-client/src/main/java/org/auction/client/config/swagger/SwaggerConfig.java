package org.auction.client.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	public SwaggerConfig(MappingJackson2HttpMessageConverter converter) {
		var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
		supportedMediaTypes.add(new MediaType("application", "octet-stream"));
		converter.setSupportedMediaTypes(supportedMediaTypes);
	}

	@Bean
	public ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper);
	}

	@Bean
	public GroupedOpenApi customTestOpenAPI() {
		String[] paths = {"/api/token", "/api/v1/auction/*", "/api/v1/auction"};

		return GroupedOpenApi.builder()
			.group("사용자를 위한 API")
			.pathsToMatch(paths)
			.build();
	}

	// 로컬 환경 및 프로덕션 환경 모두에서 테스트할 수 있도록 OpenAPI 서버 설정
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.servers(List.of(
				new Server().url("https://api.omocha-auction.com").description("Production server"),
				new Server().url("http://localhost:8080").description("Local server")  // 로컬 테스트 서버 추가
			));
	}
}