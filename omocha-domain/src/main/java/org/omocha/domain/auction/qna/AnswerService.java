package org.omocha.domain.auction.qna;

public interface AnswerService {
	AnswerInfo.CreateAnswer addAnswer(AnswerCommand.CreateAnswer createAnswerCommand);

	AnswerInfo.AnswerResponse modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand);
}
