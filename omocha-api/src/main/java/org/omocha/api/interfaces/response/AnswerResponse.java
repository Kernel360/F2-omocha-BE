package org.omocha.api.interfaces.response;

import java.time.LocalDateTime;

import org.omocha.domain.auction.qna.AnswerEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AnswerResponse(
	Long answerId,
	String title,
	String content,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
	public static AnswerResponse toDto(
		AnswerEntity answerEntity
	) {
		return new AnswerResponse(
			answerEntity.getAnswerId(),
			answerEntity.getTitle(),
			answerEntity.getContent(),
			answerEntity.getCreatedAt()
		);

	}
}
