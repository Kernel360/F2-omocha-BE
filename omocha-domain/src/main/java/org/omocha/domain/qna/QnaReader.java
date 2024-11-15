package org.omocha.domain.qna;

import org.omocha.domain.qna.answer.Answer;
import org.omocha.domain.qna.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaReader {
	Question getQuestion(Long questionId);

	Page<Qna> getQnaList(Long auctionId, Pageable sortPage);

	Answer getAnswer(Long answerId);

	boolean checkAnswerExistAtAnswer(Long questionId);
}
