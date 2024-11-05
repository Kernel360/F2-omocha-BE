package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QuestionDto {

	public record QnaServiceResponse(
		QuestionDto.QuestionDetails questionDetails,
		AnswerDto.AnswerDetails answerDetails
	) {
	}

	public record QuestionDetails(
		Long questionId,
		String title,
		String content,
		LocalDateTime createdAt,
		Long memberId,
		String email,
		String nickName,
		String profileImageUrl

	) {

	}

	public record AddQuestionRequest(
		Long auctionId,
		String title,
		String content
	) {
	}

	public record AddQuestionResponse(
		Long questionId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt

	) {

	}

	public record ModifyQuestionRequest(
		String title,
		String content
	) {
	}

	public record ModifyQuestionResponse(
		Long questionId
	) {
	}

}
