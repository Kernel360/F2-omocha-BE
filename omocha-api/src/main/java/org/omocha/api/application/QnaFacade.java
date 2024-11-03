package org.omocha.api.application;

import org.omocha.domain.auction.qna.AnswerCommand;
import org.omocha.domain.auction.qna.AnswerInfo;
import org.omocha.domain.auction.qna.AnswerService;
import org.omocha.domain.auction.qna.QuestionCommand;
import org.omocha.domain.auction.qna.QuestionInfo;
import org.omocha.domain.auction.qna.QuestionService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaFacade {

	private final QuestionService questionService;
	private final AnswerService answerService;

	public QuestionInfo.CreateQuestionResponse addQuestion(QuestionCommand.CreateQuestion createQuestionCommand) {
		return questionService.addQuestion(createQuestionCommand);
	}

	public QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand) {
		return questionService.modifyQuestion(modifyQuestionCommand);
	}

	public void removeQuestion(QuestionCommand.DeleteQuestion deleteQuestionCommand) {
		questionService.questionRemove(deleteQuestionCommand);
	}

	public AnswerInfo.CreateAnswer addAnswer(AnswerCommand.CreateAnswer createAnswerCommand) {
		return answerService.addAnswer(createAnswerCommand);
	}

	public AnswerInfo.AnswerResponse modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand) {
		return answerService.modifyAnswer(modifyAnswerCommand);
	}

	public void removeAnswer(AnswerCommand.DeleteAnswer deleteAnswerModify) {
		answerService.removeAnswer(deleteAnswerModify);
	}
}
