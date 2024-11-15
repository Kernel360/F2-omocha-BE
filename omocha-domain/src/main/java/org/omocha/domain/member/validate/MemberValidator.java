package org.omocha.domain.member.validate;

public interface MemberValidator {
	boolean isEmailDuplicateForOauth(String email);

	void validateEmail(String email);

	void validateDuplicateNickName(String nickName);
}
