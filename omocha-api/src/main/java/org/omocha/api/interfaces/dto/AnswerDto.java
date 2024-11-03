package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AnswerDto {
	public record CreateAnswerRequest(
		Long questionId,
		String title,
		String content
	) {
	}

	public record CreateAnswerResponse(
		Long questionId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt
	) {

	}

	public record ModifyAnswerRequest(
		String title,
		String content
	) {
	}

	public record AnswerResponse(
		Long answerId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {

	}

}
