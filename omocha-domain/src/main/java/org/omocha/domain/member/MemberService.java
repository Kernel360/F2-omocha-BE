package org.omocha.domain.member;

public interface MemberService {

	MemberInfo.MemberDetail addMember(MemberCommand.MemberCreate memberCreateCommand);

	MemberInfo.MemberDetail findMember(Long memberId);

	MemberInfo.Login findMember(String email);
}
