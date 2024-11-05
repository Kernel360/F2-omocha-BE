package org.omocha.domain.auction.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
	QuestionInfo.AddQuestionResponse addQuestion(QuestionCommand.AddQuestion addQuestionCommand);

	QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand);

	void questionRemove(QuestionCommand.RemoveQuestion removeQuestionCommand);

	Page<QuestionInfo.QnaServiceResponse> retriveQnaList(QuestionCommand.QnaList qnaListCommand, Pageable sortPage);
}
