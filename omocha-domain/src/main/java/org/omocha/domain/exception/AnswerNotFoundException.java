package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class AnswerNotFoundException extends QnaException {

	public AnswerNotFoundException(Long answerId) {
		super(
			ErrorCode.ANSWER_NOT_FOUND,
			"답변을 찾을 수 없습니다. answerId : " + answerId
		);
	}
}
