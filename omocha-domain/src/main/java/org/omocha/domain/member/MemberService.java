package org.omocha.domain.member;

public interface MemberService {

	MemberInfo.CurrentMemberInfo findCurrentMemberInfo(Long memberId);

	MemberInfo.MemberDetail addMember(MemberCommand.MemberCreate memberCreateCommand);

	MemberInfo.MemberDetail findMember(Long memberId);

	MemberInfo.Login findMember(String email);

	MemberInfo.MemberModifyInfo modifyBasicInfo(MemberCommand.MemberModify memberModifyCommand);

	void modifyPassword(MemberCommand.PasswordModify passwordModifyCommand);

	MemberInfo.ProfileImageInfo modifyProfileImage(MemberCommand.ProfileImageModify profileImageModifyCommand);

}
