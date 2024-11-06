package org.omocha.domain.auction.qna;

import org.omocha.domain.member.Member;

public interface QuestionValidator {
	void validAnswerExistAtAnswer(Question question);

	void hasQuestionOwnership(Question question, Member member);

}
