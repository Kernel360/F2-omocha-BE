package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QuestionDto {
	public record CreateQuestionRequest(
		Long auctionId,
		String title,
		String content
	) {
	}

	public record CreateQuestionResponse(
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

	public record QuestionResponse(
		Long questionId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		Long memberId,
		String email

		//TODO : 유저 정보 수정 필요 ex) profileImageUrl

	) {
	}

}
