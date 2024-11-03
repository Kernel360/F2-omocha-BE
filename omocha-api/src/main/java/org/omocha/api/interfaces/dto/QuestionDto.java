package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import org.omocha.domain.auction.qna.Question;

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
		public static CreateQuestionResponse toDto(Question question) {
			return new CreateQuestionResponse(
				question.getQuestionId(),
				question.getTitle(),
				question.getContent(),
				question.getCreatedAt()
			);
		}
	}

}
