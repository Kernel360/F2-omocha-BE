package org.omocha.domain.member;

import org.omocha.domain.member.vo.Email;

public interface MemberService {

	MemberInfo.RetrieveMyInfo retrieveMyInfo(Long memberId);

	MemberInfo.RetrieveMemberInfo retrieveMemberInfo(Long memberId);

	void addMember(MemberCommand.AddMember addMemberCommand);

	MemberInfo.MemberDetail retrieveMember(Long memberId);

	MemberInfo.Login retrieveMember(Email email);

	MemberInfo.ModifyMyInfo modifyMyInfo(MemberCommand.ModifyMyInfo modifyBasicInfoCommand);

	void modifyPassword(MemberCommand.ModifyPassword modifyPasswordCommand);

	MemberInfo.ModifyProfileImage modifyProfileImage(MemberCommand.ModifyProfileImage modifyProfileImageCommand);

	MemberInfo.RetrievePassword retrievePassword(Long memberId);

}
