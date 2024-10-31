package org.auction.domain.qna.domain.response;

import org.auction.domain.qna.domain.entity.AnswerEntity;
import org.auction.domain.qna.domain.entity.QuestionEntity;

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
