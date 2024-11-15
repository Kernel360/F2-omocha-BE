package org.omocha.infra.qna;

import org.omocha.domain.qna.answer.Answer;
import org.omocha.domain.qna.QnaStore;
import org.omocha.domain.qna.question.Question;
import org.omocha.infra.qna.repository.AnswerRepository;
import org.omocha.infra.qna.repository.QuestionRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QnaStoreImpl implements QnaStore {

	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;

	@Override
	public Question store(Question question) {

		return questionRepository.save(question);

	}

	@Override
	public Answer store(Answer answer) {
		return answerRepository.save(answer);
	}

}
