package org.omocha.domain.member;

import org.omocha.domain.member.vo.Email;

public interface MemberService {

	MemberInfo.RetrieveCurrentMemberInfo retrieveCurrentMemberInfo(Long memberId);

	void addMember(MemberCommand.AddMember addMemberCommand);

	MemberInfo.MemberDetail retrieveMember(Long memberId);

	MemberInfo.Login retrieveMember(Email email);

	MemberInfo.ModifyBasicInfo modifyBasicInfo(MemberCommand.ModifyBasicInfo modifyBasicInfoCommand);

	void modifyPassword(MemberCommand.ModifyPassword modifyPasswordCommand);

	MemberInfo.ModifyProfileImage modifyProfileImage(MemberCommand.ModifyProfileImage modifyProfileImageCommand);

	MemberInfo.RetrievePassword retrievePassword(Long memberId);

}
