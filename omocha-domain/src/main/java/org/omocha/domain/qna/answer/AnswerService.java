package org.omocha.domain.qna.answer;

public interface AnswerService {
	AnswerInfo.AddAnswer addAnswer(AnswerCommand.AddAnswer addAnswerCommand);

	AnswerInfo.ModifyAnswer modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand);

	void removeAnswer(AnswerCommand.RemoveAnswer removeAnswerModify);
}
