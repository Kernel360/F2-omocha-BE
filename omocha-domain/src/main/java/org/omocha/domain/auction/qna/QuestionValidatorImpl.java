package org.omocha.domain.auction.qna;

import org.omocha.domain.exception.AnswerAlreadyExistException;
import org.omocha.domain.exception.QuestionNotAllowedException;
import org.omocha.domain.member.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuestionValidatorImpl implements QuestionValidator {

	public final QnaReader qnaReader;

	@Override
	public void validModifyAndRemove(Question question) {

		if (qnaReader.existsByQuestionId(question.getQuestionId())) {
			throw new AnswerAlreadyExistException(question.getQuestionId());
		}

	}

	@Override
	public void hasQuestionOwnership(
		Question question,
		Member member
	) {

		// TODO : MemberException 처리 후 해야함
		if (!question.getMember().getMemberId().equals(member.getMemberId())) {
			throw new QuestionNotAllowedException(question.getQuestionId());
		}
	}
}
