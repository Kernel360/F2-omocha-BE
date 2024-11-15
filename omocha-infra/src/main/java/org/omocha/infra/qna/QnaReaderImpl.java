package org.omocha.infra.qna;

import org.omocha.domain.qna.answer.Answer;
import org.omocha.domain.qna.Qna;
import org.omocha.domain.qna.QnaReader;
import org.omocha.domain.qna.question.Question;
import org.omocha.infra.qna.repository.AnswerRepository;
import org.omocha.infra.qna.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kms.model.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class QnaReaderImpl implements QnaReader {

	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;

	@Override
	public Question getQuestion(Long questionId) {

		// TODO : exception 수정정필요
		return questionRepository.findByQuestionIdAndDeletedIsFalse(questionId)
			.orElseThrow(() -> new IllegalArgumentException("Question not found"));
	}

	@Override
	public Page<Qna> getQnaList(Long auctionId, Pageable sortPage) {
		return questionRepository.retrieveQnaList(auctionId, sortPage);

	}

	@Override
	public Answer getAnswer(Long answerId) {
		return answerRepository.findByAnswerIdAndDeletedIsFalse(answerId)
			.orElseThrow(() -> new NotFoundException("Not found answer"));

	}

	@Override
	public boolean checkAnswerExistAtAnswer(Long questionId) {
		return answerRepository.existsByQuestionQuestionIdAndDeletedIsFalse(questionId);
	}

}
