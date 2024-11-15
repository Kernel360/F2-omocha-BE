package org.omocha.domain.qna.answer;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public class AnswerInfo {

	public record AnswerDetails(
		Long answerId,
		String title,
		String content,
		LocalDateTime createdAt
	) {

		@QueryProjection
		public AnswerDetails(Answer answer) {
			this(answer.getAnswerId(),
				answer.getTitle(),
				answer.getContent(),
				answer.getCreatedAt()
			);
		}

		public static AnswerDetails toInfo(
			Answer answer
		) {
			return new AnswerDetails(
				answer.getAnswerId(),
				answer.getTitle(),
				answer.getContent(),
				answer.getCreatedAt()
			);

		}
	}

	public record AddAnswer(
		Long questionId,
		String title,
		String content,
		LocalDateTime createAt
	) {
		public static AddAnswer toInfo(
			Answer answer
		) {
			return new AddAnswer(
				answer.getQuestion().getQuestionId(),
				answer.getTitle(),
				answer.getContent(),
				answer.getCreatedAt()
			);

		}
	}

	public record ModifyAnswer(
		Long answerId
	) {
		public static ModifyAnswer toInfo(
			Answer answer
		) {
			return new ModifyAnswer(
				answer.getAnswerId()
			);

		}
	}

}
