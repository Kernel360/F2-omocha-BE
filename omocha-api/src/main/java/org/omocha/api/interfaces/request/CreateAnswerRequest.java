package org.omocha.api.interfaces.request;

public record CreateAnswerRequest(
	Long questionId,
	String title,
	String content
) {
}
