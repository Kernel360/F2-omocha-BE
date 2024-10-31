package org.auction.client.qna.interfaces.request;

public record ModifyAnswerRequest(
	String title,
	String content
) {
}
