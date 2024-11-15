package org.omocha.domain.qna;

import org.omocha.domain.qna.answer.Answer;
import org.omocha.domain.qna.question.Question;

public interface QnaStore {
	Question store(Question question);

	Answer store(Answer answer);
}
