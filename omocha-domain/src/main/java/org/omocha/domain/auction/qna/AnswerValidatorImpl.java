package org.omocha.domain.auction.qna;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnswerValidatorImpl implements AnswerValidator {

	@Override
	public void hasAuctionOwnership(
		Auction auction,
		Member member
	) {
		// if (!auction.getMember().getMemberId().equals(member.getMemberId())) {
		// 	throw new InvalidMemberException(INVALID_MEMBER);
		// }
	}

	@Override
	public void validateAnswerNotExists(
		Question question
	) {
		// if (answerRepository.existsByQuestionEntityAndDeletedIsFalse(question)) {
		// 	throw new QnaResponseStatusException(QnACode.EXISTING_ANSWER_CONFLICT);
		// }

	}
}
