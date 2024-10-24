package org.omocha.api.interfaces.response;

import java.time.LocalDateTime;

import org.omocha.domain.auction.qna.AnswerEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CreateAnswerResponse(
	Long questionId,
	String title,
	String content,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createAt
) {
	public static CreateAnswerResponse toDto(
		AnswerEntity answerEntity
	) {
		return new CreateAnswerResponse(
			answerEntity.getQuestionEntity().getQuestionId(),
			answerEntity.getTitle(),
			answerEntity.getContent(),
			answerEntity.getCreatedAt()
		);

	}
}
