package org.auction.client.member.application;

import static org.auction.client.common.code.MemberCode.*;

import org.auction.client.exception.member.InvalidPasswordException;
import org.auction.client.exception.member.MemberEmailAlreadyExistsException;
import org.auction.client.exception.member.MemberNotFoundException;
import org.auction.client.member.interfaces.request.MemberCreateRequest;
import org.auction.client.member.interfaces.request.MemberDuplicateRequest;
import org.auction.client.member.interfaces.request.MemberLoginRequest;
import org.auction.client.member.interfaces.response.MemberDetailResponse;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.domain.enums.Role;
import org.auction.domain.member.domain.enums.UserStatus;
import org.auction.domain.member.infrastructure.MemberRepository;
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
		MemberDuplicateRequest memberDuplicateRequest
	) {
		return memberRepository.existsByEmailAndProviderIsNull(memberDuplicateRequest.email());
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

		if (!validatePassword(memberLoginRequest, member)) {
			return null;
		}

		return member;
	}

	private boolean validatePassword(
		MemberLoginRequest memberLoginRequest,
		MemberEntity member
	) {
		if (!passwordEncoder.matches(memberLoginRequest.password(), member.getPassword())) {
			throw new InvalidPasswordException(INVALID_PASSWORD);
		}

		return true;
	}
}
