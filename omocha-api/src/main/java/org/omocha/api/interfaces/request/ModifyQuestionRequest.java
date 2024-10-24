package org.omocha.api.interfaces.request;

public record ModifyQuestionRequest(
	String title,
	String content
) {
}
