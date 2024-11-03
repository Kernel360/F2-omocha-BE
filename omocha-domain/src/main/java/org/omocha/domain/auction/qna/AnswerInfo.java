package org.omocha.domain.auction.qna;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AnswerInfo {

	public record CreateAnswer(
		Long questionId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt
	) {
		public static CreateAnswer toDto(
			Answer answer
		) {
			return new CreateAnswer(
				answer.getQuestion().getQuestionId(),
				answer.getTitle(),
				answer.getContent(),
				answer.getCreatedAt()
			);

		}
	}

	public record AnswerResponse(
		Long answerId,
		String title,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {
		public static AnswerResponse toDto(
			Answer answer
		) {
			return new AnswerResponse(
				answer.getAnswerId(),
				answer.getTitle(),
				answer.getContent(),
				answer.getCreatedAt()
			);

		}
	}

}
