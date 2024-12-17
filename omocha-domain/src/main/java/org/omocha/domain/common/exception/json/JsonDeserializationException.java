package org.omocha.domain.common.exception.json;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

public class JsonDeserializationException extends OmochaException {
	public <T> JsonDeserializationException(Class<T> clazz, String json) {
		super(
			ErrorCode.JSON_DESERIALIZATION_ERROR,
			"JSON 문자열을 객체로 역직렬화하는 과정에서 문제가 발생했습니다. clazz: " + clazz.getName() + "json: " + json
		);
	}
}

