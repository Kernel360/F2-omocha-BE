package org.omocha.domain.qna.exception;

import org.omocha.domain.common.code.ErrorCode;

public class AnswerAlreadyExistException extends QnaException {
	public AnswerAlreadyExistException(Long questionId) {
		super(
			ErrorCode.ANSWER_ALREADY_EXISTS,
			"이미 답변이 존재합니다. questionId : " + questionId
		);
	}
}
