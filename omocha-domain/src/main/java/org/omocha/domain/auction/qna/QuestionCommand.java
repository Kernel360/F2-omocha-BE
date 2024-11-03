package org.omocha.domain.auction.qna;

public class QuestionCommand {
	public record CreateQuestion(
		Long memberId,
		Long auctionId,
		String title,
		String content
	) {
	}
}
