package org.omocha.domain.auction.qna;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;

public interface AnswerValidator {

	void hasAuctionOwnership(Auction auction, Member member);

	void validateAnswerNotExists(Question question);
}
