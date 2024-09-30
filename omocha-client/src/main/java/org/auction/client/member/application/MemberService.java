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

	public MemberEntity findMemberByLoginId(
		String loginId
	) {
		return memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
	}

	public void validateLogin(
		MemberLoginRequest memberLoginRequest
	) {
		MemberEntity memberInfo = findMemberByLoginId(memberLoginRequest.loginId());

		if (!passwordEncoder.matches(memberLoginRequest.password(), memberInfo.getPassword())) {
			throw new InvalidPasswordException(INVALID_PASSWORD);
		}
	}
}
