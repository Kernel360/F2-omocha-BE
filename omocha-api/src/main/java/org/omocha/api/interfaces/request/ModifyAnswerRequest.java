package org.omocha.api.interfaces.request;

public record ModifyAnswerRequest(
	String title,
	String content
) {
}
