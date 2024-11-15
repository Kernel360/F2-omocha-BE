package org.omocha.domain.qna.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
	QuestionInfo.AddQuestion addQuestion(QuestionCommand.AddQuestion addQuestionCommand);

	QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand);

	void removeQuestion(QuestionCommand.RemoveQuestion removeQuestionCommand);

	Page<QuestionInfo.RetrieveQnas> retrieveQnas(QuestionCommand.RetrieveQnas retrieveQnasCommand, Pageable sortPage);
}
