package org.omocha.domain.member.validate;

import org.omocha.domain.member.MemberReader;
import org.omocha.domain.member.exception.MemberAlreadyExistException;
import org.omocha.domain.member.exception.MemberNicknameDuplicateException;
import org.omocha.domain.member.vo.Email;
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
	public boolean isEmailDuplicateForOauth(Email email) {

		// TODO : Exception 설정 후 수정 필요
		if (memberReader.existsByEmailAndProviderIsNull(email)) {
			throw new MemberAlreadyExistException(email);
		}
		return true;
	}

	// TODO : exception 수정 필요
	@Override
	public void validateEmail(Email email) {
		if (memberReader.existsByEmail(email)) {
			throw new MemberAlreadyExistException(email);
		}
	}

	@Override
	public void validateDuplicateNickname(String nickname) {
		if (memberReader.existsByNickname(nickname)) {
			throw new MemberNicknameDuplicateException(nickname);
		}
	}

}
