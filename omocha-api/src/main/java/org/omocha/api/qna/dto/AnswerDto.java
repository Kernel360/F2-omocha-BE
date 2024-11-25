package org.omocha.api.qna.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
		@NotNull Long questionId,
		@NotBlank String title,
		@NotBlank String content
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
		@NotBlank String title,
		@NotBlank String content
	) {
	}

	public record AnswerModifyResponse(
		Long answerId
	) {
	}

}
