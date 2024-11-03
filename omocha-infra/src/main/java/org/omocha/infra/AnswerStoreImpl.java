package org.omocha.infra;

import org.omocha.domain.auction.qna.Answer;
import org.omocha.domain.auction.qna.AnswerStore;
import org.omocha.infra.repository.AnswerRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnswerStoreImpl implements AnswerStore {

	private final AnswerRepository answerRepository;

	@Override
	public Answer store(Answer answer) {
		return answerRepository.save(answer);
	}
}
