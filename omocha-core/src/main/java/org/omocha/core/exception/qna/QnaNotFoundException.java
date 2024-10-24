package org.omocha.core.exception.qna;

import org.omocha.client.common.code.QnACode;

public class QnaNotFoundException extends QnaException {
	public QnaNotFoundException(QnACode qnaCode) {
		super(qnaCode);
	}

	public QnaNotFoundException(QnACode qnaCode, String detailMessage) {
		super(qnaCode, detailMessage);
	}
}
