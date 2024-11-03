package org.omocha.api.application;

import org.omocha.domain.auction.qna.QuestionCommand;
import org.omocha.domain.auction.qna.QuestionInfo;
import org.omocha.domain.auction.qna.QuestionService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaFacade {

	private final QuestionService questionService;

	public QuestionInfo.CreateQuestionResponse addQuestion(QuestionCommand.CreateQuestion createQuestionCommand) {
		return questionService.addQuestion(createQuestionCommand);
	}

	public QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand) {
		return questionService.modifyQuestion(modifyQuestionCommand);
	}
}
