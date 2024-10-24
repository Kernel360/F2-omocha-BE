package org.omocha.core.exception.qna;

import org.omocha.client.common.code.QnACode;

public class QnaNotAllowedException extends QnaException {
	public QnaNotAllowedException(QnACode qnaCode) {
		super(qnaCode);
	}

	public QnaNotAllowedException(QnACode qnaCode, String detailMessage) {
		super(qnaCode, detailMessage);
	}
}
