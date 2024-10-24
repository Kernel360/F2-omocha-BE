package org.auction.client.qna.interfaces.response;

import org.auction.domain.qna.AnswerEntity;
import org.auction.domain.qna.QuestionEntity;

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
