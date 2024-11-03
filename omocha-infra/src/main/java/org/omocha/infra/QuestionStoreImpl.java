package org.omocha.infra;

import org.omocha.domain.auction.qna.Question;
import org.omocha.domain.auction.qna.QuestionStore;
import org.omocha.infra.repository.QuestionRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestionStoreImpl implements QuestionStore {

	private final QuestionRepository questionRepository;

	@Override
	public Question store(Question question) {

		return questionRepository.save(question);

	}

}
