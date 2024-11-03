package org.omocha.api.application;

import org.omocha.api.common.auth.jwt.JwtProvider;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.omocha.domain.member.MemberValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;

	public MemberInfo.MemberDetail addMember(MemberCommand.MemberCreate memberCreateCommand) {

		// DB에 String으로 저장되어 여기서 변환함
		//  MypageFacade passwordModify 참고
		memberCreateCommand = new MemberCommand.MemberCreate(
			memberCreateCommand.email(),
			passwordEncoder.encode(memberCreateCommand.password())
		);

		return memberService.addMember(memberCreateCommand);
	}

	public boolean isEmailDuplicate(String email) {
		return memberValidator.isEmailDuplicateForOauth(email);
	}

	public void memberLogin(MemberCommand.MemberLogin memberLoginCommand, HttpServletResponse response) {

		MemberInfo.Login loginInfo = memberService.findMember(memberLoginCommand.email());

		//  TODO : exception 추가 필요
		if (!passwordEncoder.matches(memberLoginCommand.password(), loginInfo.password())) {
			throw new RuntimeException("Current password is incorrect");
		}

		// 인코딩 후 적용되지않아 주석처리함
		// memberValidator.validatePassword(memberLoginCommand.password(), loginInfo.password());

		jwtProvider.generateAccessToken(loginInfo.memberId(), response);
		jwtProvider.generateRefreshToken(loginInfo.memberId(), response);
	}
}
