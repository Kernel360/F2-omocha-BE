package org.omocha.api.application;

import static org.omocha.client.common.code.MemberCode.*;

import org.omocha.client.exception.member.InvalidPasswordException;
import org.omocha.client.exception.member.MemberEmailAlreadyExistsException;
import org.omocha.client.exception.member.MemberNotFoundException;
import org.omocha.client.member.interfaces.request.MemberCreateRequest;
import org.omocha.client.member.interfaces.request.MemberLoginRequest;
import org.omocha.client.member.interfaces.response.MemberDetailResponse;
import org.omocha.domain.member.MemberEntity;
import org.omocha.domain.member.Role;
import org.omocha.domain.member.UserStatus;
import org.omocha.infra.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberDetailResponse addMember(
		MemberCreateRequest memberCreateRequest
	) {
		if (memberRepository.existsByEmail(memberCreateRequest.email())) {
			throw new MemberEmailAlreadyExistsException(MEMBER_ALREADY_EXISTS);
		}

		MemberEntity member = MemberEntity.builder()
			.email(memberCreateRequest.email())
			.password(passwordEncoder.encode(memberCreateRequest.password()))
			.role(Role.ROLE_USER)
			.userStatus(UserStatus.ACTIVATE)
			.build();

		return MemberDetailResponse.toDto(memberRepository.save(member));
	}

	public boolean isEmailDuplicate(
		String email
	) {
		if (memberRepository.existsByEmailAndProviderIsNull(email)) {
			throw new MemberEmailAlreadyExistsException(MEMBER_ALREADY_EXISTS);
		}

		return true;
	}

	// TODO : 아래 두개의 메서드에서 에러가 발생했을 경우 각각 식별이 필요함
	//  Exception의 명확한 네이밍 => MemberNotFoundByIdException, MemberNotFoundByEmailException
	public MemberEntity findMember(
		Long memberId
	) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
	}

	public MemberEntity findMember(
		MemberLoginRequest memberLoginRequest
	) {
		MemberEntity member = memberRepository.findByEmail(memberLoginRequest.email())
			.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

		validatePassword(memberLoginRequest, member);

		return member;
	}

	private void validatePassword(
		MemberLoginRequest memberLoginRequest,
		MemberEntity member
	) {
		if (!passwordEncoder.matches(memberLoginRequest.password(), member.getPassword())) {
			throw new InvalidPasswordException(INVALID_PASSWORD);
		}
	}
}
