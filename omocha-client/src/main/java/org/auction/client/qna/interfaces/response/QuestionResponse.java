package org.auction.client.qna.interfaces.response;

import java.time.LocalDateTime;

import org.auction.domain.qna.domain.entity.QuestionEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record QuestionResponse(
	Long questionId,
	String title,
	String content,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt,
	Long memberId,
	String email

) {
	public static QuestionResponse toDto(
		QuestionEntity questionEntity
	) {
		return new QuestionResponse(
			questionEntity.getQuestionId(),
			questionEntity.getTitle(),
			questionEntity.getContent(),
			questionEntity.getCreatedAt(),
			questionEntity.getMemberEntity().getMemberId(),
			questionEntity.getMemberEntity().getEmail()

		);

	}
}
