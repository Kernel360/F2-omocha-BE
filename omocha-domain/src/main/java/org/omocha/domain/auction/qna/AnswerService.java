package org.omocha.domain.auction.qna;

public interface AnswerService {
	AnswerInfo.AddAnswer addAnswer(AnswerCommand.AddAnswer addAnswerCommand);

	AnswerInfo.ModifyAnswer modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand);

	void removeAnswer(AnswerCommand.RemoveAnswer removeAnswerModify);
}
