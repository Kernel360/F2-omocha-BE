package org.omocha.api.qna.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
		String email,
		String nickName,
		String profileImageUrl

	) {

	}

	public record QuestionAddRequest(
		Long auctionId,
		String title,
		String content
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
		String title,
		String content
	) {
	}

	public record QuestionModifyResponse(
		Long questionId
	) {
	}

}
