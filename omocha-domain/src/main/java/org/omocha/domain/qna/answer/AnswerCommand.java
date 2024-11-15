package org.omocha.domain.qna.answer;

import org.omocha.domain.qna.question.Question;

public class AnswerCommand {
	public record AddAnswer(
		Long memberId,
		Long questionId,
		String title,
		String content
	) {
		public Answer toEntity(Question question) {
			return Answer.builder()
				.title(title)
				.content(content)
				.question(question)
				.build();
		}
	}

	public record ModifyAnswer(
		Long memberId,
		Long answerId,
		String title,
		String content
	) {
	}

	public record RemoveAnswer(
		Long memberId,
		Long answerId
	) {
	}

}
