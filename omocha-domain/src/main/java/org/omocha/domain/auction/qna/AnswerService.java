package org.omocha.domain.auction.qna;

public interface AnswerService {
	AnswerInfo.CreateAnswer addAnswer(AnswerCommand.CreateAnswerRequest createAnswerCommand);
}
