package org.omocha.domain.auction.qna;

public class AnswerCommand {
	public record CreateAnswerRequest(
		Long memberId,
		Long questionId,
		String title,
		String content
	) {
	}

}
