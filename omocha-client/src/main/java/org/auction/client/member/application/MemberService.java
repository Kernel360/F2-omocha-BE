package org.auction.client.member.application;

import static org.auction.client.common.code.MemberCode.*;

import org.auction.client.exception.member.InvalidPasswordException;
import org.auction.client.exception.member.MemberAlreadyExistsException;
import org.auction.client.exception.member.MemberNotFoundException;
import org.auction.client.member.interfaces.request.MemberCreateRequest;
import org.auction.client.member.interfaces.request.MemberLoginRequest;
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

	public String addMember(
		MemberCreateRequest memberCreateRequest
	) {
		if (memberRepository.existsByLoginId(memberCreateRequest.loginId())) {
			throw new MemberAlreadyExistsException(MEMBER_ALREADY_EXISTS);
		}

		MemberEntity memberEntity = MemberEntity.builder()
			.loginId(memberCreateRequest.loginId())
			.password(passwordEncoder.encode(memberCreateRequest.password()))
			.role(Role.ROLE_USER)
			.userStatus(UserStatus.ACTIVATE)
			.build();

		return memberRepository.save(memberEntity).getLoginId();
	}

	// TODO : findMemberByMemberId와 findMemberByLoginId에서 에러가 발생했을 경우 각각 식별이 필요함
	public MemberEntity findMemberByMemberId(
		Long memberId
	) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
	}

	public MemberEntity findMemberByLoginId(
		MemberLoginRequest memberLoginRequest
	) {
		MemberEntity member = memberRepository.findByLoginId(memberLoginRequest.loginId())
			.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

		if (!validateLogin(memberLoginRequest, member)) {
			return null;
		}

		return member;
	}

	private boolean validateLogin(
		MemberLoginRequest memberLoginRequest,
		MemberEntity memberEntity
	) {
		if (!passwordEncoder.matches(memberLoginRequest.password(), memberEntity.getPassword())) {
			throw new InvalidPasswordException(INVALID_PASSWORD);
		}

		return true;
	}
}
