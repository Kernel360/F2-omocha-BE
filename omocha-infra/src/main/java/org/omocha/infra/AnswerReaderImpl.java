package org.omocha.infra;

import org.omocha.domain.auction.qna.Answer;
import org.omocha.domain.auction.qna.AnswerReader;
import org.omocha.infra.repository.AnswerRepository;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kms.model.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnswerReaderImpl implements AnswerReader {

	private final AnswerRepository answerRepository;

	@Override
	public Answer findAnswer(Long answerId) {
		return answerRepository.findByAnswerIdAndDeletedIsFalse(answerId)
			.orElseThrow(() -> new NotFoundException("Not found answer"));

	}
}
