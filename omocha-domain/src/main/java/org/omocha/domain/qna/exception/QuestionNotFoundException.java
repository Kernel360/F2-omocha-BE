package org.omocha.domain.qna.exception;

import org.omocha.domain.common.code.ErrorCode;

public class QuestionNotFoundException extends QnaException {
	public QuestionNotFoundException(Long questionId) {
		super(
			ErrorCode.QUESTION_NOT_FOUND,
			"문의를 찾을 수 없습니다. questionId : " + questionId

		);
	}
}
