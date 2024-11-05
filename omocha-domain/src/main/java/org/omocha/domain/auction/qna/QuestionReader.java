package org.omocha.domain.auction.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionReader {
	Question findQuestion(Long questionId);

	Page<Qna> findQnaList(Long auctionId, Pageable sortPage);
}
