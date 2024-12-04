package org.omocha.api.member;

import org.omocha.api.common.util.PasswordManager;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {

	private final MemberService memberService;
	private final PasswordManager passwordManager;

	public MemberInfo.RetrieveMyInfo retrieveMyInfo(Long memberId) {
		return memberService.retrieveMyInfo(memberId);
	}

	public MemberInfo.RetrieveMemberInfo retrieveMemberInfo(Long memberId) {
		return memberService.retrieveMemberInfo(memberId);
	}

	public MemberInfo.ModifyMyInfo modifyMyInfo(MemberCommand.ModifyMyInfo modifyMyInfo) {
		return memberService.modifyMyInfo(modifyMyInfo);
	}

	public void modifyPassword(MemberCommand.ModifyPassword modifyPasswordCommand) {

		passwordManager.validateIdenticalPassword(
			modifyPasswordCommand.currentPassword(),
			modifyPasswordCommand.newEncryptedPassword(),
			modifyPasswordCommand.memberId()
		);

		MemberInfo.RetrievePassword retrievePasswordInfo = memberService.retrievePassword(
			modifyPasswordCommand.memberId()
		);

		passwordManager.match(
			modifyPasswordCommand.currentPassword(),
			retrievePasswordInfo.password(),
			modifyPasswordCommand.memberId()
		);

		memberService.modifyPassword(modifyPasswordCommand);
	}

	public MemberInfo.ModifyProfileImage modifyProfileImage(
		MemberCommand.ModifyProfileImage modifyProfileImageCommand) {

		return memberService.modifyProfileImage(modifyProfileImageCommand);
	}

}
