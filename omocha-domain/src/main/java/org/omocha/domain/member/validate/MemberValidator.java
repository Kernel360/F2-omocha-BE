package org.omocha.domain.member.validate;

import org.omocha.domain.member.vo.Email;

public interface MemberValidator {
	boolean isEmailDuplicateForOauth(Email email);

	void validateEmail(Email email);

	void validateDuplicateNickname(String nickname);
}
