package org.omocha.api.application;

import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {

	private final MemberService memberService;

	public MemberInfo.MemberDetailInfo addMember(
		MemberCommand.MemberCreateCommand memberCreateCommand
	) {
		MemberInfo.MemberDetailInfo memberDetailInfo = memberService.addMember(memberCreateCommand);

		return memberDetailInfo;

	}

	// public boolean isEmailDuplicate(
	// 	MemberCommand.MemberDuplicateCommand memberDuplicateCommand
	// ) {
	// 	memberService.isEmailDuplicate(memberDuplicateCommand);
	//
	// }
	//
	// public Member findMember(
	// 	Long memberId
	// ) {
	// 	memberService.findMember(memberId);
	//
	// }
	//
	// public Member findMember(
	// 	MemberDto.MemberLoginRequest memberLoginRequest
	// ) {
	// 	memberService.findMember(memberLoginRequest);
	//
	// }

}
