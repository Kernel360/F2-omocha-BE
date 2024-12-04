package org.omocha.api.auth;

import org.omocha.api.auth.dto.AuthDto;
import org.omocha.api.auth.jwt.JwtProvider;
import org.omocha.api.common.util.PasswordManager;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.omocha.domain.member.validate.MemberValidator;
import org.omocha.domain.member.vo.Email;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacade {

	private final MemberService memberService;
	private final MemberValidator memberValidator;
	private final JwtProvider jwtProvider;
	private final PasswordManager passwordManager;

	public void addMember(MemberCommand.AddMember addMemberCommand) {
		memberService.addMember(addMemberCommand);
	}

	public boolean isEmailDuplicate(Email email) {
		return memberValidator.isEmailDuplicateForOauth(email);
	}

	public AuthDto.JwtResponse loginMember(MemberCommand.LoginMember loginMemberCommand) {
		MemberInfo.Login loginInfo = memberService.retrieveMember(loginMemberCommand.email());
		passwordManager.match(loginMemberCommand.password(), loginInfo.encryptedPassword(), loginInfo.memberId());

		return jwtProvider.generateToken(loginInfo.memberId());
	}

	public AuthDto.JwtResponse reissueToken(MemberCommand.ReissueToken reissueTokenCommand) {
		return jwtProvider.reissueToken(reissueTokenCommand);
	}
}
