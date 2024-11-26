package org.omocha.api.qna.dto;

import java.time.LocalDateTime;

import org.omocha.domain.member.vo.Email;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionDto {

	public record QnaListResponse(
		QuestionDto.QuestionDetails questionDetails,
		AnswerDto.AnswerDetails answerDetails
	) {
	}

	public record QuestionDetails(
		Long questionId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		Long memberId,
		Email email,
		String nickname,
		String profileImageUrl
	) {
	}

	public record QuestionAddRequest(
		@NotNull Long auctionId,
		@NotBlank String title,
		@NotBlank String content
	) {
	}

	public record QuestionAddResponse(
		Long questionId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt

	) {
	}

	public record QuestionModifyRequest(
		@NotBlank String title,
		@NotBlank String content
	) {
	}

	public record QuestionModifyResponse(
		Long questionId
	) {
	}

}
