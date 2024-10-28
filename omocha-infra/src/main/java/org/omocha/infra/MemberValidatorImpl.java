package org.omocha.infra;

import org.omocha.domain.member.MemberReader;
import org.omocha.domain.member.MemberValidator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class MemberValidatorImpl implements MemberValidator {

	private final MemberReader memberReader;

	@Override
	public boolean isEmailDuplicate(String email) {

		// TODO : Exception 설정 후 수정 필요
		if (memberReader.existsByEmailAndProviderIsNull(email)) {
			// throw new MemberEmailAlreadyExistsException(MEMBER_ALREADY_EXISTS);
		}
		return true;
	}
}
