package org.omocha.domain.qna.question;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;

public class QuestionCommand {

	public record RetrieveQnas(
		Long auctionId
	) {
	}

	public record AddQuestion(
		Long memberId,
		Long auctionId,
		String title,
		String content
	) {
		public Question toEntity(Member member, Auction auction) {
			return Question.builder()
				.member(member)
				.auction(auction)
				.title(title)
				.content(content)
				.build();
		}
	}

	public record ModifyQuestion(
		Long memberId,
		Long questionId,
		String title,
		String content
	) {
	}

	public record RemoveQuestion(
		Long memberId,
		Long questionId
	) {
	}

}
