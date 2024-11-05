package org.omocha.domain.auction.qna;

public interface AnswerService {
	AnswerInfo.AddAnswer addAnswer(AnswerCommand.AddAnswer addAnswerCommand);

	AnswerInfo.ModifyAnswerResponse modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand);

	void removeAnswer(AnswerCommand.RemoveAnswer removeAnswerModify);
}
