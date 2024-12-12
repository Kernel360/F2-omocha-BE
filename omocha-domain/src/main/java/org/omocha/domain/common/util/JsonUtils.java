package org.omocha.domain.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {

	private static final ObjectMapper mapper = createObjectMapper();

	private static ObjectMapper createObjectMapper() {
		return new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
	}

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to serialize object", e);
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to serialize object", e);
		}
	}
}
