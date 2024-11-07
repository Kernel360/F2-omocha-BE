package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class QuestionNotFoundException extends QnaException {
	public QuestionNotFoundException(Long questionId) {
		super(
			ErrorCode.QUESTION_NOT_FOUND,
			"문의를 찾을 수 없습니다. questionId : " + questionId

		);
	}
}
