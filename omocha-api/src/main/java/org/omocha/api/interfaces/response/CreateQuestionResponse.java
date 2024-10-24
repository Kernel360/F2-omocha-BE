package org.omocha.api.interfaces.response;

import java.time.LocalDateTime;

import org.omocha.domain.auction.qna.QuestionEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CreateQuestionResponse(
	Long questionId,
	String title,
	String content,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createAt

) {
	public static CreateQuestionResponse toDto(QuestionEntity questionEntity) {
		return new CreateQuestionResponse(
			questionEntity.getQuestionId(),
			questionEntity.getTitle(),
			questionEntity.getContent(),
			questionEntity.getCreatedAt()
		);
	}
}
