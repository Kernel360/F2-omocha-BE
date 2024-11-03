package org.omocha.domain.auction.qna;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QuestionInfo {
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
