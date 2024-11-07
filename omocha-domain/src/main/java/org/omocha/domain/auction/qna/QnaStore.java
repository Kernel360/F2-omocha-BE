package org.omocha.domain.auction.qna;

public interface QnaStore {
	Question store(Question question);

	Answer store(Answer answer);
}
