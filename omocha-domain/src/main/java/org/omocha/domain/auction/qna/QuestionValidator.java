package org.omocha.domain.auction.qna;

import org.omocha.domain.member.Member;

public interface QuestionValidator {
	void validModifyAndRemove(Question question);

	void hasQuestionOwnership(
		Question question,
		Member member
	);
}
