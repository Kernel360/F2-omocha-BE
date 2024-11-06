package org.omocha.domain.auction.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaReader {
	Question getQuestion(Long questionId);

	Page<Qna> getQnaList(Long auctionId, Pageable sortPage);

	Answer getAnswer(Long answerId);

	boolean checkAnswerExistAtAnswer(Long questionId);
}
