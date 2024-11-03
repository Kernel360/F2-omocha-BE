package org.omocha.domain.auction.qna;

public class AnswerCommand {
	public record CreateAnswer(
		Long memberId,
		Long questionId,
		String title,
		String content
	) {
	}

	public record ModifyAnswer(
		Long memberId,
		Long answerId,
		String title,
		String content
	) {
	}

	public record DeleteAnswer(
		Long memberId,
		Long answerId,
		String title,
		String content
	) {
	}

}
