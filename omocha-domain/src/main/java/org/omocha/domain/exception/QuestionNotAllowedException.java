package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class QuestionNotAllowedException extends QnaException {
	public QuestionNotAllowedException(Long questionId) {
		super(
			ErrorCode.QUESTION_DENY,
			"수정,삭제가 거부되었습니다. questionId : " + questionId
		);
	}
}
