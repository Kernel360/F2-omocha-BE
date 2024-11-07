package org.omocha.domain.member;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class MemberValidatorImpl implements MemberValidator {

	private final MemberReader memberReader;

	// TODO : VO 구성 후 MemberPasswordValidator, MemberEmailValidator 논의

	@Override
	public boolean isEmailDuplicateForOauth(String email) {

		// TODO : Exception 설정 후 수정 필요
		if (memberReader.existsByEmailAndProviderIsNull(email)) {
			// throw new MemberEmailAlreadyExistsException(MEMBER_ALREADY_EXISTS);
		}
		return true;
	}

	// TODO : exception 수정 필요
	@Override
	public void validateEmail(String email) {
		if (memberReader.existsByEmail(email)) {
			// throw new MemberEmailAlreadyExistsException(MEMBER_ALREADY_EXISTS);
		}
	}

}
