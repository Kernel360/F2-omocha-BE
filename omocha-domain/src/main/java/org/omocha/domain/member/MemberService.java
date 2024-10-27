package org.omocha.domain.member;

public interface MemberService {

	public MemberInfo.MemberDetailInfo addMember(MemberCommand.MemberCreateCommand memberCreateCommand);

	// public boolean isEmailDuplicate(MemberCommand.MemberDuplicateCommand memberDuplicateCommand);

	// public Member findMember(Long memberId);

	// public Member findMember(
	// 	MemberLoginRequest memberLoginRequest
	// )
	//
	// private void validatePassword(
	// 	MemberLoginRequest memberLoginRequest,
	// 	Member member
	// )

}
