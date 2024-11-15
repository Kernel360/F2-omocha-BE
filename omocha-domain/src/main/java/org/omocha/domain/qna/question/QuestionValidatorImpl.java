package org.omocha.domain.qna.question;

import org.omocha.domain.qna.QnaReader;
import org.omocha.domain.qna.exception.AnswerAlreadyExistException;
import org.omocha.domain.qna.exception.QuestionNotAllowedException;
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
	public void validAnswerExistAtAnswer(Question question) {

		if (qnaReader.checkAnswerExistAtAnswer(question.getQuestionId())) {
			throw new AnswerAlreadyExistException(question.getQuestionId());
		}

	}

	@Override
	public void hasQuestionOwnership(Question question, Member member) {

		// TODO : MemberException 처리 후 해야함
		if (!question.getMember().getMemberId().equals(member.getMemberId())) {
			throw new QuestionNotAllowedException(question.getQuestionId());
		}
	}
}
