package org.auction.client.exception.qna;

import org.auction.client.common.code.QnACode;

public class QnaResponseStatusException extends QnaException {
	public QnaResponseStatusException(QnACode qnaCode) {
		super(qnaCode);
	}

	public QnaResponseStatusException(QnACode qnaCode, String detailMessage) {
		super(qnaCode, detailMessage);
	}
}
