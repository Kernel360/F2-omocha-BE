package org.omocha.domain.qna.answer;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;
import org.omocha.domain.qna.question.Question;

public interface AnswerValidator {

	void hasAuctionOwnership(Auction auction, Member member);

	void validateAnswerNotExists(Question question);
}
