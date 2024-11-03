package org.omocha.domain.auction.qna;

import org.omocha.domain.member.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuestionValidatorImpl implements QuestionValidator {

	@Override
	public void validModifyAndRemove(Question question) {

		// Answer 추가 후 수정 필요

		// if (answerRepository.existsByQuestionEntityAndDeletedIsFalse(question)) {
		// 	// throw new QnaNotAllowedException(QnACode.QUESTION_DENY);
		// }

	}

	@Override
	public void hasQuestionOwnership(
		Question question,
		Member member
	) {
		if (!question.getMember().getMemberId().equals(member.getMemberId())) {
			// throw new InvalidMemberException(INVALID_MEMBER);
		}
	}
}
