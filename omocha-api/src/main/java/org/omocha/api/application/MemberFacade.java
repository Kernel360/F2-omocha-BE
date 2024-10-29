package org.omocha.api.application;

import org.omocha.api.common.auth.jwt.JwtProvider;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.omocha.domain.member.MemberValidator;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {

	private final MemberService memberService;
	private final MemberValidator memberValidator;
	private final JwtProvider jwtProvider;

	public MemberInfo.MemberDetail addMember(MemberCommand.MemberCreate memberCreateCommand) {
		return memberService.addMember(memberCreateCommand);
	}

	public boolean isEmailDuplicate(String email) {
		return memberValidator.isEmailDuplicateForOauth(email);
	}

	public void memberLogin(MemberCommand.MemberLogin memberLoginCommand, HttpServletResponse response) {
		MemberInfo.Login loginInfo = memberService.findMember(memberLoginCommand.email());
		memberValidator.validatePassword(memberLoginCommand.password(), loginInfo.password());

		jwtProvider.generateAccessToken(loginInfo.memberId(), response);
		jwtProvider.generateRefreshToken(loginInfo.memberId(), response);
	}
}
