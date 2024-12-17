package org.omocha.domain.common.exception.json;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

public class JsonSerializationException extends OmochaException {
	public JsonSerializationException(Object obj) {
		super(
			ErrorCode.JSON_SERIALIZATION_ERROR,
			"객체를 JSON 문자열로 직렬화하는 과정에서 문제가 발생했습니다. Object: " + obj.getClass().getName()
		);
	}
}

