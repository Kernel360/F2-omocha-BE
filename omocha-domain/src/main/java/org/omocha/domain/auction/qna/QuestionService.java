package org.omocha.domain.auction.qna;

import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
	QuestionInfo.CreateQuestionResponse addQuestion(QuestionCommand.CreateQuestion createQuestionCommand);
}
