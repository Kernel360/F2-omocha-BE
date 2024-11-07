package org.omocha.domain.auction.qna;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.exception.AnswerAlreadyExistException;
import org.omocha.domain.member.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnswerValidatorImpl implements AnswerValidator {

	private final QnaReader qnaReader;

	@Override
	public void hasAuctionOwnership(Auction auction, Member member) {
		// if (!auction.getMemberId().equals(member.getMemberId())) {
		// 	throw new InvalidMemberException(INVALID_MEMBER);
		// }
	}

	@Override
	public void validateAnswerNotExists(Question question) {
		if (qnaReader.checkAnswerExistAtAnswer(question.getQuestionId())) {
			throw new AnswerAlreadyExistException(question.getQuestionId());
		}

	}
}
