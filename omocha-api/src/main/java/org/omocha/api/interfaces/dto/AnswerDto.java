package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AnswerDto {

	public record AnswerDetails(
		Long answerId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {
	}

	public record AnswerAddRequest(
		Long questionId,
		String title,
		String content
	) {
	}

	public record AnswerAddResponse(
		Long questionId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt
	) {
	}

	public record AnswerModifyRequest(
		String title,
		String content
	) {
	}

	public record AnswerModifyResponse(
		Long answerId
	) {
	}

}
