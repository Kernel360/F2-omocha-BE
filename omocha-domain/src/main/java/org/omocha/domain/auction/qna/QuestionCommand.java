package org.omocha.domain.auction.qna;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;

public class QuestionCommand {

	public record QnaList(
		Long auctionId
	) {
	}

	// @PathVariable(value = "auctionId") Long auctionId,
	// @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
	// @RequestParam(value = "direction", defaultValue = "ASC") String direction,
	// @PageableDefault(page = 0, size = 10)
	// Pageable pageable

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
