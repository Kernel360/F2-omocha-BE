package org.omocha.domain.qna.exception;

import org.omocha.domain.common.code.ErrorCode;

public class AnswerNotAllowedException extends QnaException {
	public AnswerNotAllowedException(Long answerId) {
		super(
			ErrorCode.ANSWER_DENY,
			"수정,삭제가 거부되었습니다. answerId : " + answerId
		);
	}
}
