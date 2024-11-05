package org.omocha.infra;

import org.omocha.domain.auction.qna.Qna;
import org.omocha.domain.auction.qna.Question;
import org.omocha.domain.auction.qna.QuestionReader;
import org.omocha.infra.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuestionReaderImpl implements QuestionReader {

	private final QuestionRepository questionRepository;

	@Override
	public Question findQuestion(Long questionId) {

		// TODO : exception 수정정필요
		return questionRepository.findByQuestionIdAndDeletedIsFalse(questionId)
			.orElseThrow(() -> new IllegalArgumentException("Question not found"));
	}

	@Override
	public Page<Qna> findQnaList(Long auctionId, Pageable sortPage) {
		return questionRepository.retribeQnaList(auctionId, sortPage);

	}

}
