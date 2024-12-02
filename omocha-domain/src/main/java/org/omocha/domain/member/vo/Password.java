package org.omocha.domain.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;

// TODO: 현재는 클라이언트에서 해싱처리를 진행하고 서버에서 BCrypt 암호화를 진행하기에 따로 검증할 내용이 없음, 타입 안정성을 위해 VO 사용
//       (추후 클라이언트에서 평문을 보내주면 검증 로직 추가)
public record Password(String value) {

	@JsonValue
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}