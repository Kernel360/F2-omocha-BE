package org.auction.domain.qna;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QnaDomainResponse {

	private QuestionEntity questionEntity;

	private AnswerEntity answerEntity;

	@QueryProjection
	public QnaDomainResponse(QuestionEntity questionEntity, AnswerEntity answerEntity) {
		this.questionEntity = questionEntity;
		this.answerEntity = answerEntity;
	}
}
