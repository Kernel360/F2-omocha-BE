package org.omocha.infra;

import org.omocha.domain.auction.qna.Answer;
import org.omocha.domain.auction.qna.QnaStore;
import org.omocha.domain.auction.qna.Question;
import org.omocha.infra.repository.AnswerRepository;
import org.omocha.infra.repository.QuestionRepository;
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
