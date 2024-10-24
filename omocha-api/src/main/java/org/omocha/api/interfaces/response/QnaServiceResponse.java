package org.omocha.api.interfaces.response;

import org.omocha.domain.auction.qna.AnswerEntity;
import org.omocha.domain.auction.qna.QuestionEntity;

public record QnaServiceResponse(
	QuestionResponse questionResponse,
	AnswerResponse answerResponse

) {
	public static QnaServiceResponse toDto(
		QuestionEntity questionEntity,
		AnswerEntity answerEntity
	) {

		return new QnaServiceResponse(
			QuestionResponse.toDto(questionEntity),
			answerEntity != null ? AnswerResponse.toDto(answerEntity) : null
		);
	}

}
