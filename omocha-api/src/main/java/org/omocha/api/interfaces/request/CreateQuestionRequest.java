package org.omocha.api.interfaces.request;

public record CreateQuestionRequest(
	Long auctionId,
	String title,
	String content
) {
}
