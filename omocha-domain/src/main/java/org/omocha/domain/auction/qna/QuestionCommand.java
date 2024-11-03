package org.omocha.domain.auction.qna;

public class QuestionCommand {
	public record CreateQuestion(
		Long memberId,
		Long auctionId,
		String title,
		String content
	) {
	}

	public record ModifyQuestion(
		Long memberId,
		Long questionId,
		String title,
		String content
	) {
	}

	public record DeleteQuestion(
		Long memberId,
		Long questionId
	) {
	}

}
