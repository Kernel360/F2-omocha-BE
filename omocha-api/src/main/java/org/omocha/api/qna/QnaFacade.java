package org.omocha.api.qna;

import org.omocha.domain.qna.answer.AnswerCommand;
import org.omocha.domain.qna.answer.AnswerInfo;
import org.omocha.domain.qna.answer.AnswerService;
import org.omocha.domain.qna.question.QuestionCommand;
import org.omocha.domain.qna.question.QuestionInfo;
import org.omocha.domain.qna.question.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaFacade {

	private final QuestionService questionService;
	private final AnswerService answerService;

	public QuestionInfo.AddQuestion addQuestion(QuestionCommand.AddQuestion addQuestionCommand) {
		return questionService.addQuestion(addQuestionCommand);
	}

	public QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand) {
		return questionService.modifyQuestion(modifyQuestionCommand);
	}

	public void removeQuestion(QuestionCommand.RemoveQuestion removeQuestionCommand) {
		questionService.removeQuestion(removeQuestionCommand);
	}

	public AnswerInfo.AddAnswer addAnswer(AnswerCommand.AddAnswer addAnswerCommand) {
		return answerService.addAnswer(addAnswerCommand);
	}

	public AnswerInfo.ModifyAnswer modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand) {
		return answerService.modifyAnswer(modifyAnswerCommand);
	}

	public void removeAnswer(AnswerCommand.RemoveAnswer removeAnswerModify) {
		answerService.removeAnswer(removeAnswerModify);
	}

	public Page<QuestionInfo.RetrieveQnas> retrieveQnas(QuestionCommand.RetrieveQnas retrieveQnasCommand,
		Pageable sortPage) {
		return questionService.retrieveQnas(retrieveQnasCommand, sortPage);
	}
}
